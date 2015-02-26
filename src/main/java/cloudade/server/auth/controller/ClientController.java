package cloudade.server.auth.controller;

import java.security.Principal;
import java.util.Collection;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.common.util.RandomValueStringGenerator;
import org.springframework.security.oauth2.provider.client.BaseClientDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cloudade.server.auth.controller.editor.AuthorityPropertyEditor;
import cloudade.server.auth.controller.editor.SplitCollectionEditor;
import cloudade.server.auth.mongo.oauth.MongoClientDetailsService;

@Controller
@RequestMapping("/client")
public class ClientController {

	@InitBinder
	public void initBinder(WebDataBinder binder) {
		binder.registerCustomEditor(Collection.class, new SplitCollectionEditor(Set.class, ","));
		binder.registerCustomEditor(GrantedAuthority.class, new AuthorityPropertyEditor());
	}

	@Autowired
	private MongoClientDetailsService clientDetailsService;

	private final static String REDIRECT_CLIENT_LIST = "redirect:/client/list";

	@RequestMapping("/list")
	public String getList(Model model, Principal principal) {
		model.addAttribute("clientDetails", clientDetailsService.listClientDetails());
		return "clients";
	}

	@RequestMapping(method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_OAUTH_ADMIN')")
	public String get(Model model) {
		model.addAttribute("clientDetails", new BaseClientDetails());
		return "form";
	}

	@RequestMapping(method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_OAUTH_ADMIN')")
	public String post(@ModelAttribute BaseClientDetails clientDetails) {

		if (clientDetails.getClientSecret() == null || clientDetails.getClientSecret().isEmpty()){
			RandomValueStringGenerator random = new RandomValueStringGenerator();
			random.setLength(12);
			clientDetails.setClientSecret(random.generate());
		}
		clientDetailsService.addClientDetails(clientDetails);

		return REDIRECT_CLIENT_LIST;
	}

	@RequestMapping(value = "/{clientId}", method = RequestMethod.GET)
	@PreAuthorize("hasRole('ROLE_OAUTH_ADMIN')")
	public String getUser(@PathVariable("clientId") String clientId, Model model) {
		model.addAttribute("clientDetails", clientDetailsService.loadClientByClientId(clientId));
		return "form";
	}

	@RequestMapping(value = "/{clientId}", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_OAUTH_ADMIN')")
	public String postUser(
			@PathVariable("clientId") String clientId,
			@ModelAttribute BaseClientDetails clientDetails) {

		// does not update the secret!
		clientDetailsService.updateClientDetails(clientDetails);

		if (clientDetails.getClientSecret() != null && !clientDetails.getClientSecret().isEmpty()) {
			clientDetailsService.updateClientSecret(
					clientDetails.getClientId(),
					clientDetails.getClientSecret());
		}

		return REDIRECT_CLIENT_LIST;
	}

	@RequestMapping(value = "/{clientId}/delete", method = RequestMethod.POST)
	@PreAuthorize("hasRole('ROLE_OAUTH_ADMIN')")
	public String deleteClient(@PathVariable("clientId") String clientId) {
		clientDetailsService.removeClientDetails(clientId);
		return REDIRECT_CLIENT_LIST;
	}




}
