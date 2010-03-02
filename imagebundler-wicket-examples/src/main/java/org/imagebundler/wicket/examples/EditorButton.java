package org.imagebundler.wicket.examples;

import org.apache.wicket.markup.html.image.Image;
import org.imagebundler.wicket.ImageBundle;
import org.imagebundler.wicket.Resource;

@ImageBundle(locale = { "en", "en_US" })
public interface EditorButton
{

	public Image bold(String id);

	public Image code(String id);

	public Image h1(String id);

	public Image hr(String id);

	@Resource("icon/img.png")
	public Image img(String id);

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
