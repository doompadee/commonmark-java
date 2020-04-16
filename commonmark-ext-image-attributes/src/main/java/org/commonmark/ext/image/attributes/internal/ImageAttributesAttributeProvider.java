package org.commonmark.ext.image.attributes.internal;

import org.commonmark.ext.image.attributes.ImageAttributes;
import org.commonmark.node.AbstractVisitor;
import org.commonmark.node.CustomNode;
import org.commonmark.node.Image;
import org.commonmark.node.Node;
import org.commonmark.renderer.html.AttributeProvider;

import java.util.*;

public class ImageAttributesAttributeProvider implements AttributeProvider {

    private ImageAttributesAttributeProvider() {
    }

    public static ImageAttributesAttributeProvider create() {
        return new ImageAttributesAttributeProvider();
    }

    @Override
    public void setAttributes(Node node, String tagName, final Map<String, String> attributes) {
        if (node instanceof Image) {
            node.accept(new AbstractVisitor() {
                @Override
                public void visit(CustomNode node) {
                    if (node instanceof ImageAttributes) {
                        ImageAttributes imageAttributes = (ImageAttributes) node;
                        for (String s : imageAttributes.getAttributes().split("\\s+")) {
                            String[] attribute = s.split("=");
                            if (attribute.length > 1) {
                                attributes.put(attribute[0], attribute[1]);
                            }
                        }
                        // Now that we have used the image attributes we remove the node.
                        imageAttributes.unlink();
                    }
                }
            });
        }
    }
}
