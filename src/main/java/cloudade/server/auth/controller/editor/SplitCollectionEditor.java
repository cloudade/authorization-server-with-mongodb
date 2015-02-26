package cloudade.server.auth.controller.editor;

import org.springframework.beans.propertyeditors.CustomCollectionEditor;

import java.util.Collection;


public class SplitCollectionEditor extends CustomCollectionEditor {

    @SuppressWarnings("rawtypes")
	private final Class<? extends Collection> collectionType;
    private final String splitRegex;

    @SuppressWarnings("rawtypes")
	public SplitCollectionEditor(Class<? extends Collection> collectionType, String splitRegex) {
        super(collectionType, true);
        this.collectionType = collectionType;
        this.splitRegex = splitRegex;
    }

    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        if (text == null || text.isEmpty()) {
            super.setValue(super.createCollection(this.collectionType, 0));
        } else {
            super.setValue(text.split(splitRegex));
        }
    }
}
