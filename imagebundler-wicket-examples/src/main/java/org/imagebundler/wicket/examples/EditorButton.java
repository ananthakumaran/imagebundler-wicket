package org.imagebundler.wicket.examples;

import org.apache.wicket.markup.html.image.Image;
import org.imagebundler.wicket.ImageBundle;
import org.imagebundler.wicket.ImageItem;
import org.imagebundler.wicket.Resource;

@ImageBundle
public interface EditorButton
{
	public Image h1(String id);

	@Resource("icon/img.png")
	public ImageItem img();

	@Resource("icon/italic.png")
	public Image italic(String id);

	@Resource("icon/ol.png")
	public Image list(String id);

	@Resource("icon/redo.png")
	public Image redo(String id);

	@Resource("icon/separator.png")
	public Image sep(String id);

	@Resource("icon/undo.png")
	public Image undo(String id);
}
