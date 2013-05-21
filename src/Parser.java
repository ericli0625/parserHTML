import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {

	public static void main(String[] args) throws Exception {

		String url = "http://okgo.tw/buty/taipei.html";

		Document doc = Jsoup.parse(new URL(url).openStream(), "big5", url);

		Element table = doc.select("table").get(6);
		Iterator<Element> ite = table.select("tr").iterator();

		// while (ite.hasNext()) {
		//
		// System.out.println(ite.next().text());
		//
		// }

		// while (ite.hasNext()) {
		//
		// String[] locals = ite.next().text().split(" ");
		// for (String local : locals) {
		// System.out.println(local);
		// }
		//
		// }

		ArrayList<String> arrayStrings = new ArrayList<String>();

		Elements links = doc.select("table").get(6).select("a[href]");

		for (Element element : links) {
			if (!element.attr("href").substring(0, 4).equals("town")) {
				// System.out.println(element.attr("href"));
				arrayStrings.add(element.attr("href"));
			}
		}

		// 02765.html; error
		arrayStrings.remove("02765.html");
		arrayStrings.remove("01789.html");

		int count_tt=1;
		
		for (String element : arrayStrings) {

			String web = "http://okgo.tw/buty/" + element;

			Document doc_1 = Jsoup
					.parse(new URL(web).openStream(), "big5", web);
			Element table_1 = doc_1.select("table").get(6);

			Iterator<Element> ite_1 = table_1.select("tr").iterator();

			// String s1 = "照";
			// ite_1.next().remove();
			//
			// while (ite_1.hasNext()) {
			//
			// String temp = ite_1.next().text();
			//
			// if (temp.length() != 0) {
			//
			// // 篩選"照片來源"
			// if (temp.substring(0, 1).equals(s1)) {
			// String[] words = temp.split(" ");
			//
			// int count = 0;
			//
			// for (String word : words) {
			// if (count != 0) {
			// System.out.print(word);
			// }
			// count++;
			// }
			// System.out.println("\r");
			// } else {
			// System.out.println(temp);
			// }
			//
			// }
			//
			// break;
			// }

			String s2 = "景點名稱";// necessary
			String s3 = "景點類型";// necessary
			String s4 = "地區位置";// necessary

			String s5 = "聯絡電話";
			String s6 = "相關網站";

			String s7 = "景點地址";

			String s8 = "聯絡傳真";
			String s9 = "營業資訊";

			Element table_2 = doc_1.select("table").get(11);
			Elements links_1 = doc_1.select("table").get(11).select("a[href]");

			Iterator<Element> ite_2 = table_2.select("tr").iterator();
			
			while (ite_2.hasNext()) {

				String temp_word = ite_2.next().text();

				if (temp_word.substring(0, 4).equals(s6)) {
					
					if (!temp_word.equals("")) {
						String[] words = temp_word.split(" ");

						int count = 0;

						for (String word : words) {
							if (count != 0) {
								System.out.println(count_tt+" "+word);
							}
							count++;
						}
					}else{
						System.out.println(count_tt+" ");
					}

				}
			
				
//				 for (Element link : links_1) {
//				 if (link.attr("href").substring(0, 4).equals("http")) {
//					 System.out.println(link.attr("href"));
//				 }
//				 }
			}
			
			count_tt++;
			
		}
	}
}