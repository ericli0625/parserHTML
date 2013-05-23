import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class Parser {

	public static void main(String[] args) throws Exception {

//		String url = "http://okgo.tw/buty/yilan.html";
//		String url = "http://okgo.tw/buty/hualien.html";
//		String url = "http://okgo.tw/buty/taitung.html";
//		String url = "http://okgo.tw/buty/penghu.html";
		String url = "http://okgo.tw/buty/kinmen.html";
//		String url = "http://okgo.tw/buty/lianjiang.html";
		
		Document doc = Jsoup.parse(new URL(url).openStream(), "big5", url);

		Element table = doc.select("table").get(6);
		Iterator<Element> ite = table.select("tr").iterator();

		// 解析首頁景點

		while (ite.hasNext()) {

			String[] locals = ite.next().text().split(" ");
			for (String local : locals) {
				// System.out.println(local);
			}

		}

		ArrayList<String> arrayStrings = new ArrayList<String>();

		Elements links = doc.select("table").get(6).select("a[href]");

		for (Element element : links) {
			if (!element.attr("href").substring(0, 4).equals("town")) {
				// System.out.println(element.attr("href"));
				arrayStrings.add(element.attr("href"));
			}
		}

		int count_tt = 1;

		ArrayList<Spotdetail> spotdetailaArrayList = new ArrayList<Spotdetail>();

		for (String element : arrayStrings) {

			Spotdetail data = new Spotdetail();

			String web = "http://okgo.tw/buty/" + element;

			Document doc_1 = Jsoup
					.parse(new URL(web).openStream(), "big5", web);
			Element table_1 = doc_1.select("table").get(6);

			Iterator<Element> ite_1 = table_1.select("tr").iterator();

			String s1 = "照";
			ite_1.next().remove();
			
			// 景點內容
			while (ite_1.hasNext()) {

				String temp = ite_1.next().text();

				if (temp.length() != 0) {

					// 篩選"照片來源"
					if (temp.substring(0, 1).equals(s1)) {
						String[] words = temp.split(" ");

						int count = 0;

						for (String word : words) {
							if (count != 0) {
//								System.out.print(word);
							}
							count++;
						}
//						System.out.println("\r");
					} else {
//						System.out.println(temp);
					}

				}

				break;
			}
			// 景點內容

			String s2 = "景點名稱";// necessary
			String s3 = "景點類型";// necessary
			String s4 = "地區位置";// necessary
			String s5 = "景點地址";
			// String s6 = "相關網站";
			String s7 = "聯絡電話";
			// String s8 = "聯絡傳真";
			// String s9 = "營業資訊";

			Element table_2 = doc_1.select("table").get(11);
			Element table_3 = doc_1.select("table").get(12);

			String temp_word_array = new String();

			data.seta1(count_tt);

			temp_word_array=ParserDetail(table_2, count_tt, s2);
			data.seta2(temp_word_array);
			temp_word_array=ParserDetail(table_2, count_tt, s3);
			data.seta3(temp_word_array);
			temp_word_array=ParserDetail(table_2, count_tt, s4);
			data.seta4(temp_word_array);
			temp_word_array=ParserDetail(table_2, count_tt, s5);
			data.seta5(temp_word_array);
			temp_word_array=ParserDetail(table_2, count_tt, s7);
			data.seta7(temp_word_array);
			
			if (data.geta2().equals("")) {
				temp_word_array=ParserDetail(table_3, count_tt, s2);
				data.seta2(temp_word_array);
				temp_word_array=ParserDetail(table_3, count_tt, s3);
				data.seta3(temp_word_array);
				temp_word_array=ParserDetail(table_3, count_tt, s4);
				data.seta4(temp_word_array);
				temp_word_array=ParserDetail(table_3, count_tt, s5);
				data.seta5(temp_word_array);
				temp_word_array=ParserDetail(table_3, count_tt, s7);
				data.seta7(temp_word_array);
			}
			
			// ParserDetail(table_3,count_tt,s7);

			spotdetailaArrayList.add(data);

			count_tt++;

		}
		
		for (Spotdetail spotdetail : spotdetailaArrayList) {
			System.out.println(spotdetail.a1 + "," + spotdetail.a2 + ","+ spotdetail.a3 + "," + spotdetail.a4 + ","+ spotdetail.a5 + "," + spotdetail.a7+ ",");
		}
		
	}

	public static String ParserDetail(Element table_2, int count_tt,
			String table_name) {

		Iterator<Element> ite_2 = table_2.select("tr").iterator();

		String temp_word_array = new String();

		while (ite_2.hasNext()) {

			String temp_word = ite_2.next().text();

			if (!temp_word.equals("")) {
				if (temp_word.substring(0, 4).equals(table_name)
						&& temp_word.length() < 20) {

					String[] words = temp_word.split(" ");

					int count = 0;

					for (String word : words) {
						if (count != 0) {
							temp_word_array = word;
//							System.out.println(count_tt + " " + word);
						}
						count++;
					}

				}
			}
			// System.out.println(count_tt + " "+temp_word);
		}

		return temp_word_array;
		
	}

	public static class Spotdetail {

		private int a1;
		private String a2; // = "景點名稱";// necessary
		private String a3; // = "景點類型";// necessary
		private String a4; // = "地區位置";// necessary
		private String a5; // = "聯絡電話";
		private String a7; // = "景點地址";

		public Spotdetail() {

		}

		public void seta1(int value) {
			this.a1 = value;
		}

		public void seta2(String value) {
			this.a2 = value;
		}

		public void seta3(String value) {
			this.a3 = value;
		}

		public void seta4(String value) {
			this.a4 = value;
		}

		public void seta5(String value) {
			this.a5 = value;
		}

		public void seta7(String value) {
			this.a7 = value;
		}

		public int geta1() {
			return this.a1;
		}

		public String geta2() {
			return this.a2;
		}

		public String geta3() {
			return this.a3;
		}

		public String geta4() {
			return this.a4;
		}

		public String geta5() {
			return this.a5;
		}

		public String geta7() {
			return this.a7;
		}
		
	}

}
