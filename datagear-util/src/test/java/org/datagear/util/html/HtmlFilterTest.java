/*
 * Copyright 2018 datagear.tech
 *
 * Licensed under the LGPLv3 license:
 * http://www.gnu.org/licenses/lgpl-3.0.html
 */

package org.datagear.util.html;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.util.HashMap;
import java.util.Map;

import org.datagear.util.html.HtmlFilter.FilterContext;
import org.junit.Test;

/**
 * {@linkplain HtmlFilter}单元测试用例。
 * 
 * @author datagear@163.com
 *
 */
public class HtmlFilterTest
{
	private HtmlFilter htmlFilter = new HtmlFilter();

	@Test
	public void filterTest_htmlReader_htmlWriter() throws IOException
	{
		{
			String html = "<html lang='zh'>"
					+ "\n"
					+ "<head>"
					+ "\n"
					+ "<title>title</title>"
					+ "\n"
					+ "<link href=\"...\">"
					+ "\n"
					+ "<script type='text/javascript' href=\"v\"></script>"
					+ "\n"
					+ "<script type='text/javascript'>"
					+ "\n"
					+ "\n"
					+ "  // line-comment <div></div> <script> </script>"
					+ "\n"
					+ "  var str='<div></div> </script>'; "
					+ "  /* block comment "
					+ "  <div></div> <script> </script>"
					+ "\n"
					+ "  <div></div> <script> </script>"
					+ "  */"
					+ "\n"
					+ "\n"
					+ "</script>"
					+ "\n"
					+ "</head>"
					+ "\n"
					+ "<body p0 p1 = v1 p2='v2' p3=\"v3\" p4 p5 = v5 >"
					+ "\n"
					+ "<!---->"
					+ "\n"
					+ "<!--comment-->"
					+ "\n"
					+ "<!-- comment -->"
					+ "\n"
					+ "<!--"
					+ "\n"
					+ " comment "
					+ "\n"
					+ " <div>comment</div>"
					+ " -->"
					+ "\n"
					+ "<input name='name' value=\"value\">"
					+ "\n"
					+ "<input name='name' value=\"value\"/>"
					+ "\n"
					+ "<input name='name' value=\"value\" />"
					+ "\n"
					+ "<div p0=v0 p1 p2 = v2>sdf</div>"
					+ "\n"
					+ "<script />"
					+ "\n"
					+ "<>"
					+ "\n"
					+ "</>"
					+ "\n"
					+ "< />"
					+ "\n"
					+ "</ >"
					+ "\n"
					+ "</body>"
					+ "\n"
					+ "</html>";

			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();

			htmlFilter.filter(in, out);

			assertEquals(html, out.toString());
		}
	}

	@Test
	public void filterTest_htmlReader_htmlWriter_tagListener() throws IOException
	{
		{
			String html = "<html lang='zh'>"
					+ "\n"
					+ "<head>"
					+ "\n"
					+ "<title>title</title>"
					+ "\n"
					+ "<link href=\"...\">"
					+ "\n"
					+ "<script type='text/javascript' href=\"v\"></script>"
					+ "\n"
					+ "<script type='text/javascript'>"
					+ "\n"
					+ "\n"
					+ "  // line-comment <div></div> <script> </script>"
					+ "\n"
					+ "  var str='<div></div> </script>'; "
					+ "  /* block comment "
					+ "  <div></div> <script> </script>"
					+ "\n"
					+ "  <div></div> <script> </script>"
					+ "  */"
					+ "\n"
					+ "\n"
					+ "</script>"
					+ "\n"
					+ "</head>"
					+ "\n"
					+ "<body p0 p1 = v1 p2='v2' p3=\"v3\" p4 p5 = v5 >"
					+ "\n"
					+ "<!---->"
					+ "\n"
					+ "<!--comment-->"
					+ "\n"
					+ "<!-- comment -->"
					+ "\n"
					+ "<!--"
					+ "\n"
					+ " comment "
					+ "\n"
					+ " <div>comment</div>"
					+ " -->"
					+ "\n"
					+ "<input name='name' value=\"value\">"
					+ "\n"
					+ "<input name='name' value=\"value\"/>"
					+ "\n"
					+ "<input name='name' value=\"value\" />"
					+ "\n"
					+ "<div p0=v0 p1 p2 = v2>sdf</div>"
					+ "\n"
					+ "<script/>"
					+ "\n"
					+ "<script p0=v0/>"
					+ "\n"
					+ "<script />"
					+ "\n"
					+ "<script p0=v0 />"
					+ "\n"
					+ "<>"
					+ "\n"
					+ "</>"
					+ "\n"
					+ "< />"
					+ "\n"
					+ "</ >"
					+ "\n"
					+ "</body>"
					+ "\n"
					+ "</html>";

			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();

			htmlFilter.filter(in, out, TEST_TAG_LISTENER);

			String expected = "[bts]<html lang='zh'[bte]>[ate]"
					+ "\n"
					+ "[bts]<head[bte]>[ate]"
					+ "\n"
					+ "[bts]<title[bte]>[ate]title[bts]</title[bte]>[ate]"
					+ "\n"
					+ "[bts]<link href=\"...\"[bte]>[ate]"
					+ "\n"
					+ "[bts]<script type='text/javascript' href=\"v\"[bte]>[ate][bts]</script[bte]>[ate]"
					+ "\n"
					+ "[bts]<script type='text/javascript'[bte]>[ate]"
					+ "\n"
					+ "\n"
					+ "  // line-comment <div></div> <script> </script>"
					+ "\n"
					+ "  var str='<div></div> </script>'; "
					+ "  /* block comment "
					+ "  <div></div> <script> </script>"
					+ "\n"
					+ "  <div></div> <script> </script>"
					+ "  */"
					+ "\n"
					+ "\n"
					+ "[bts]</script[bte]>[ate]"
					+ "\n"
					+ "[bts]</head[bte]>[ate]"
					+ "\n"
					+ "[bts]<body p0 p1 = v1 p2='v2' p3=\"v3\" p4 p5 = v5 [bte]>[ate]"
					+ "\n"
					+ "<!---->"
					+ "\n"
					+ "<!--comment-->"
					+ "\n"
					+ "<!-- comment -->"
					+ "\n"
					+ "<!--"
					+ "\n"
					+ " comment "
					+ "\n"
					+ " <div>comment</div>"
					+ " -->"
					+ "\n"
					+ "[bts]<input name='name' value=\"value\"[bte]>[ate]"
					+ "\n"
					+ "[bts]<input name='name' value=\"value\"[bte]/>[ate]"
					+ "\n"
					+ "[bts]<input name='name' value=\"value\" [bte]/>[ate]"
					+ "\n"
					+ "[bts]<div p0=v0 p1 p2 = v2[bte]>[ate]sdf[bts]</div[bte]>[ate]"
					+ "\n"
					+ "[bts]<script[bte]/>[ate]"
					+ "\n"
					+ "[bts]<script p0=v0[bte]/>[ate]"
					+ "\n"
					+ "[bts]<script [bte]/>[ate]"
					+ "\n"
					+ "[bts]<script p0=v0 [bte]/>[ate]"
					+ "\n"
					+ "[bts]<[bte]>[ate]"
					+ "\n"
					+ "[bts]</[bte]>[ate]"
					+ "\n"
					+ "[bts]< [bte]/>[ate]"
					+ "\n"
					+ "[bts]</ [bte]>[ate]"
					+ "\n"
					+ "[bts]</body[bte]>[ate]"
					+ "\n"
					+ "[bts]</html[bte]>[ate]";

			assertEquals(expected, out.toString());
		}
	}

	@Test
	public void filterTest_htmlReader() throws IOException
	{
		{
			String html = "<html><head><meta charset='UTF-8'></head><body></body></html>";
			StringReader in = new StringReader(html);
			htmlFilter.filter(in);
		}
	}

	@Test
	public void filterTest_htmlReader_tagListener() throws IOException
	{
		{
			String html = "<html><head><meta charset='UTF-8'></head><body></body></html>";
			CharsetTagListener tl = new CharsetTagListener();
			StringReader in = new StringReader(html);
			htmlFilter.filter(in, tl);

			assertEquals("UTF-8", tl.getCharset());
		}
	}

	@Test
	public void filterTest_aborted() throws IOException
	{
		{
			String html = "<html><head><meta charset=\"UTF-8\"></head><body></body></html>";

			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();

			CharsetTagListener tagListener = new CharsetTagListener(true);
			htmlFilter.filter(in, out, tagListener);

			assertEquals("<html><head><meta charset=\"UTF-8\">", out.toString());
			assertEquals("UTF-8", tagListener.getCharset());
		}
		{
			String html = "<html><head><meta content=\"text/html; charset=UTF-8\"></head><body></body></html>";

			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();

			CharsetTagListener tagListener = new CharsetTagListener(true);
			htmlFilter.filter(in, out, tagListener);

			assertEquals("<html><head><meta content=\"text/html; charset=UTF-8\">", out.toString());
			assertEquals("UTF-8", tagListener.getCharset());
		}
	}

	@Test
	public void readTagNameTest() throws IOException
	{
		{
			StringReader in = new StringReader("div ");
			StringBuilder tagName = new StringBuilder();
			String nameAfter = htmlFilter.readTagName(in, tagName);

			assertEquals("div", tagName.toString());
			assertEquals(" ", nameAfter);
		}

		{
			StringReader in = new StringReader("div>");
			StringBuilder tagName = new StringBuilder();
			String nameAfter = htmlFilter.readTagName(in, tagName);

			assertEquals("div", tagName.toString());
			assertEquals(">", nameAfter);
		}

		{
			StringReader in = new StringReader("div");
			StringBuilder tagName = new StringBuilder();
			String nameAfter = htmlFilter.readTagName(in, tagName);

			assertEquals("div", tagName.toString());
			assertNull(nameAfter);
		}

		{
			StringReader in = new StringReader("div/>");
			StringBuilder tagName = new StringBuilder();
			String nameAfter = htmlFilter.readTagName(in, tagName);

			assertEquals("div", tagName.toString());
			assertEquals("/>", nameAfter);
		}

		{
			StringReader in = new StringReader("/div ");
			StringBuilder tagName = new StringBuilder();
			String nameAfter = htmlFilter.readTagName(in, tagName);

			assertEquals("/div", tagName.toString());
			assertEquals(" ", nameAfter);
		}

		{
			StringReader in = new StringReader(">");
			StringBuilder tagName = new StringBuilder();
			String nameAfter = htmlFilter.readTagName(in, tagName);

			assertEquals("", tagName.toString());
			assertEquals(">", nameAfter);
		}

		{
			StringReader in = new StringReader(" ");
			StringBuilder tagName = new StringBuilder();
			String nameAfter = htmlFilter.readTagName(in, tagName);

			assertEquals("", tagName.toString());
			assertEquals(" ", nameAfter);
		}

		{
			StringReader in = new StringReader("/>");
			StringBuilder tagName = new StringBuilder();
			String nameAfter = htmlFilter.readTagName(in, tagName);

			assertEquals("/", tagName.toString());
			assertEquals(">", nameAfter);
		}

		{
			StringReader in = new StringReader(" />");
			StringBuilder tagName = new StringBuilder();
			String nameAfter = htmlFilter.readTagName(in, tagName);

			assertEquals("", tagName.toString());
			assertEquals(" ", nameAfter);
		}
	}

	@Test
	public void writeAfterHtmlCommentTest() throws IOException
	{
		{
			String html = "!---->";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			StringBuilder tagName = new StringBuilder();
			String nameAfter = htmlFilter.readTagName(in, tagName);

			htmlFilter.writeAfterHtmlComment(in, out, emptyFilterContext(), tagName.toString(), nameAfter);
			assertEquals("<!---->", out.toString());
		}

		{
			String html = "!-- comment -->";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			StringBuilder tagName = new StringBuilder();
			String nameAfter = htmlFilter.readTagName(in, tagName);

			htmlFilter.writeAfterHtmlComment(in, out, emptyFilterContext(), tagName.toString(), nameAfter);
			assertEquals("<!-- comment -->", out.toString());
		}

		{
			String html = "!-- <div></div> -->";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			StringBuilder tagName = new StringBuilder();
			String nameAfter = htmlFilter.readTagName(in, tagName);

			htmlFilter.writeAfterHtmlComment(in, out, emptyFilterContext(), tagName.toString(), nameAfter);
			assertEquals("<!-- <div></div> -->", out.toString());
		}

		{
			String html = "!----><after>";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			StringBuilder tagName = new StringBuilder();
			String nameAfter = htmlFilter.readTagName(in, tagName);

			htmlFilter.writeAfterHtmlComment(in, out, emptyFilterContext(), tagName.toString(), nameAfter);
			assertEquals("<!---->", out.toString());
		}

		{
			String html = "!----<after>";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			StringBuilder tagName = new StringBuilder();
			String nameAfter = htmlFilter.readTagName(in, tagName);

			htmlFilter.writeAfterHtmlComment(in, out, emptyFilterContext(), tagName.toString(), nameAfter);
			assertEquals("<!----<after>", out.toString());
		}
	}

	@Test
	public void writeAfterTagTest() throws IOException
	{
		{
			{
				String html = "div";
				StringReader in = new StringReader(html);
				StringWriter out = new StringWriter();
				StringBuilder tagName = new StringBuilder();
				String nameAfter = htmlFilter.readTagName(in, tagName);

				htmlFilter.writeAfterTag(in, out, emptyFilterContext(), tagName.toString(), nameAfter);
				assertEquals("<div", out.toString());
			}

			{
				String html = "div>after";
				StringReader in = new StringReader(html);
				StringWriter out = new StringWriter();
				StringBuilder tagName = new StringBuilder();
				String nameAfter = htmlFilter.readTagName(in, tagName);

				htmlFilter.writeAfterTag(in, out, emptyFilterContext(), tagName.toString(), nameAfter);
				assertEquals("<div>", out.toString());
			}

			{
				String html = "div/>after";
				StringReader in = new StringReader(html);
				StringWriter out = new StringWriter();
				StringBuilder tagName = new StringBuilder();
				String nameAfter = htmlFilter.readTagName(in, tagName);

				htmlFilter.writeAfterTag(in, out, emptyFilterContext(), tagName.toString(), nameAfter);
				assertEquals("<div/>", out.toString());
			}

			{
				String html = "div p0=v0 >after";
				StringReader in = new StringReader(html);
				StringWriter out = new StringWriter();
				StringBuilder tagName = new StringBuilder();
				String nameAfter = htmlFilter.readTagName(in, tagName);

				htmlFilter.writeAfterTag(in, out, emptyFilterContext(), tagName.toString(), nameAfter);
				assertEquals("<div p0=v0 >", out.toString());
			}
		}

		{
			{
				String html = "div";
				StringReader in = new StringReader(html);
				StringWriter out = new StringWriter();
				StringBuilder tagName = new StringBuilder();
				String nameAfter = htmlFilter.readTagName(in, tagName);

				htmlFilter.writeAfterTag(in, out, tagListenerFilterContext(), tagName.toString(), nameAfter);
				assertEquals("[bts]<div[bte]", out.toString());
			}

			{
				String html = "div>after";
				StringReader in = new StringReader(html);
				StringWriter out = new StringWriter();
				StringBuilder tagName = new StringBuilder();
				String nameAfter = htmlFilter.readTagName(in, tagName);

				htmlFilter.writeAfterTag(in, out, tagListenerFilterContext(), tagName.toString(), nameAfter);
				assertEquals("[bts]<div[bte]>[ate]", out.toString());
			}

			{
				String html = "div/>after";
				StringReader in = new StringReader(html);
				StringWriter out = new StringWriter();
				StringBuilder tagName = new StringBuilder();
				String nameAfter = htmlFilter.readTagName(in, tagName);

				htmlFilter.writeAfterTag(in, out, tagListenerFilterContext(), tagName.toString(), nameAfter);
				assertEquals("[bts]<div[bte]/>[ate]", out.toString());
			}

			{
				String html = "div p0=v0 >after";
				StringReader in = new StringReader(html);
				StringWriter out = new StringWriter();
				StringBuilder tagName = new StringBuilder();
				String nameAfter = htmlFilter.readTagName(in, tagName);

				htmlFilter.writeAfterTag(in, out, tagListenerFilterContext(), tagName.toString(), nameAfter);
				assertEquals("[bts]<div p0=v0 [bte]>[ate]", out.toString());
			}
		}
	}

	@Test
	public void writeUntilTagEndTest() throws IOException
	{
		{
			String html = ">";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			String tagEnd = htmlFilter.writeUntilTagEnd(in, out, emptyFilterContext(), "");

			assertEquals("", out.toString());
			assertEquals(">", tagEnd);
		}
		{
			String html = "p0='v0' p1='v1>' p2=\"p2>\">";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			String tagEnd = htmlFilter.writeUntilTagEnd(in, out, emptyFilterContext(), "");

			assertEquals("p0='v0' p1='v1>' p2=\"p2>\"", out.toString());
			assertEquals(">", tagEnd);
		}
		{
			String html = "p0='>";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			String tagEnd = htmlFilter.writeUntilTagEnd(in, out, emptyFilterContext(), "");

			assertEquals("p0='>", out.toString());
			assertNull(tagEnd);
		}
		{
			String html = "p0=\">";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			String tagEnd = htmlFilter.writeUntilTagEnd(in, out, emptyFilterContext(), "");

			assertEquals("p0=\">", out.toString());
			assertNull(tagEnd);
		}
		{
			String html = "/>";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			String tagEnd = htmlFilter.writeUntilTagEnd(in, out, emptyFilterContext(), "");

			assertEquals("", out.toString());
			assertEquals("/>", tagEnd);
		}
		{
			String html = " />";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			String tagEnd = htmlFilter.writeUntilTagEnd(in, out, emptyFilterContext(), "");

			assertEquals(" ", out.toString());
			assertEquals("/>", tagEnd);
		}
	}

	@Test
	public void writeUntilTagEndTest_tagAttrs() throws IOException
	{
		{
			String html = ">";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			Map<String, String> tagAttrs = new HashMap<String, String>();
			String tagEnd = htmlFilter.writeUntilTagEnd(in, out, emptyFilterContext(), "", tagAttrs);

			assertEquals("", out.toString());
			assertEquals(">", tagEnd);
			assertTrue(tagAttrs.isEmpty());
		}
		{
			String html = "p0=v0 p1='v1>' p2=\"v2>\" p3 p4 = v4 p5 = 'v5' >";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			Map<String, String> tagAttrs = new HashMap<String, String>();
			String tagEnd = htmlFilter.writeUntilTagEnd(in, out, emptyFilterContext(), "", tagAttrs);

			assertEquals("p0=v0 p1='v1>' p2=\"v2>\" p3 p4 = v4 p5 = 'v5' ", out.toString());
			assertEquals(">", tagEnd);
			assertEquals(6, tagAttrs.size());
			assertEquals("v0", tagAttrs.get("p0"));
			assertEquals("v1>", tagAttrs.get("p1"));
			assertEquals("v2>", tagAttrs.get("p2"));
			assertNull(tagAttrs.get("p3"));
			assertEquals("v4", tagAttrs.get("p4"));
			assertEquals("v5", tagAttrs.get("p5"));
		}
		{
			String html = "p0='>";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			Map<String, String> tagAttrs = new HashMap<String, String>();
			String tagEnd = htmlFilter.writeUntilTagEnd(in, out, emptyFilterContext(), "", tagAttrs);

			assertEquals("p0='>", out.toString());
			assertNull(tagEnd);
			assertEquals(1, tagAttrs.size());
			assertEquals("'>", tagAttrs.get("p0"));
		}
		{
			String html = "p0=\">";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			Map<String, String> tagAttrs = new HashMap<String, String>();
			String tagEnd = htmlFilter.writeUntilTagEnd(in, out, emptyFilterContext(), "", tagAttrs);

			assertEquals("p0=\">", out.toString());
			assertNull(tagEnd);
			assertEquals(1, tagAttrs.size());
			assertEquals("\">", tagAttrs.get("p0"));
		}
		{
			String html = "/>";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			Map<String, String> tagAttrs = new HashMap<String, String>();
			String tagEnd = htmlFilter.writeUntilTagEnd(in, out, emptyFilterContext(), "", tagAttrs);

			assertEquals("", out.toString());
			assertEquals("/>", tagEnd);
			assertTrue(tagAttrs.isEmpty());
		}
		{
			String html = " />";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();
			Map<String, String> tagAttrs = new HashMap<String, String>();
			String tagEnd = htmlFilter.writeUntilTagEnd(in, out, emptyFilterContext(), "", tagAttrs);

			assertEquals(" ", out.toString());
			assertEquals("/>", tagEnd);
			assertTrue(tagAttrs.isEmpty());
		}
	}

	@Test
	public void writeAfterScriptCloseTagTest() throws IOException
	{
		{
			String html = "</script>";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();

			htmlFilter.writeAfterScriptCloseTag(in, out, emptyFilterContext());
			assertEquals("</script>", out.toString());
		}
		{
			String html = "</script>after";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();

			htmlFilter.writeAfterScriptCloseTag(in, out, emptyFilterContext());
			assertEquals("</script>", out.toString());
		}
		{
			String html = "'</script>' </script>after";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();

			htmlFilter.writeAfterScriptCloseTag(in, out, emptyFilterContext());
			assertEquals("'</script>' </script>", out.toString());
		}
		{
			String html = "\"</script>\" </script>after";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();

			htmlFilter.writeAfterScriptCloseTag(in, out, emptyFilterContext());
			assertEquals("\"</script>\" </script>", out.toString());
		}
		{
			String html = "`</script>` </script>after";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();

			htmlFilter.writeAfterScriptCloseTag(in, out, emptyFilterContext());
			assertEquals("`</script>` </script>", out.toString());
		}
		{
			String html = "//comment </script> \n </script>after";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();

			htmlFilter.writeAfterScriptCloseTag(in, out, emptyFilterContext());
			assertEquals("//comment </script> \n </script>", out.toString());
		}
		{
			String html = "/*comment </script> \n </script> \n comment*/ </script>after";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();

			htmlFilter.writeAfterScriptCloseTag(in, out, emptyFilterContext());
			assertEquals("/*comment </script> \n </script> \n comment*/ </script>", out.toString());
		}
		{
			String html = "<div> <span></span> </script>after";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();

			htmlFilter.writeAfterScriptCloseTag(in, out, emptyFilterContext());
			assertEquals("<div> <span></span> </script>", out.toString());
		}
		{
			String html = "<div> <span></span> </script>after";
			StringReader in = new StringReader(html);
			StringWriter out = new StringWriter();

			htmlFilter.writeAfterScriptCloseTag(in, out, tagListenerFilterContext());
			assertEquals("<div> <span></span> [bts]</script[bte]>[ate]", out.toString());
		}
	}

	protected FilterContext emptyFilterContext()
	{
		return new FilterContext();
	}

	protected FilterContext tagListenerFilterContext()
	{
		return new FilterContext(TEST_TAG_LISTENER);
	}

	protected static TagListener TEST_TAG_LISTENER = new DefaultTagListener()
	{
		@Override
		public void beforeTagStart(Reader in, Writer out, String tagName) throws IOException
		{
			out.write("[bts]");
		}

		@Override
		public void beforeTagEnd(Reader in, Writer out, String tagName, String tagEnd,
				Map<String, String> attrs) throws IOException
		{
			out.write("[bte]");
		}

		@Override
		public boolean afterTagEnd(Reader in, Writer out, String tagName, String tagEnd) throws IOException
		{
			out.write("[ate]");
			return false;
		}
	};
}