package pu.hui.util;

import java.util.ArrayList;

public class JsonFormatUtil {
	/**
	 * json�ַ�ĸ�ʽ��
	 * 
	 * @param json
	 * @return
	 */
	public static String formatJson(String json) {
		if (json == null || json.trim().length() == 0) {
			return null;
		}

		int fixedLenth = 0;
		ArrayList<String> tokenList = new ArrayList<String>();
		{
			String jsonTemp = json;
			while (jsonTemp.length() > 0) {
				String token = getToken(jsonTemp);
				jsonTemp = jsonTemp.substring(token.length());
				token = token.trim();
				tokenList.add(token);
			}
		}

		for (int i = 0; i < tokenList.size(); i++) {
			String token = tokenList.get(i);
			int length = token.getBytes().length;
			if (length > fixedLenth && i < tokenList.size() - 1
					&& tokenList.get(i + 1).equals(":")) {
				fixedLenth = length;
			}
		}

		StringBuilder buf = new StringBuilder();
		int count = 0;
		for (int i = 0; i < tokenList.size(); i++) {

			String token = tokenList.get(i);

			if (token.equals(",")) {
				buf.append(token);
				doFill(buf, count);
				continue;
			}
			if (token.equals(":")) {
				buf.append(token);
				continue;
			}
			if (token.equals("{")) {
				String nextToken = tokenList.get(i + 1);
				if (nextToken.equals("}")) {
					i++;
					buf.append("{ }");
				} else {
					count++;
					buf.append(token);
					doFill(buf, count);
				}
				continue;
			}
			if (token.equals("}")) {
				count--;
				doFill(buf, count);
				buf.append(token);
				continue;
			}
			if (token.equals("[")) {
				String nextToken = tokenList.get(i + 1);
				if (nextToken.equals("]")) {
					i++;
					buf.append("[ ]");
				} else {
					count++;
					buf.append(token);
					doFill(buf, count);
				}
				continue;
			}
			if (token.equals("]")) {
				count--;
				doFill(buf, count);
				buf.append(token);
				continue;
			}

			buf.append(token);
		}
		return buf.toString();
	}

	private static String getToken(String json) {
		StringBuilder buf = new StringBuilder();
		boolean isInYinHao = false;
		while (json.length() > 0) {
			String token = json.substring(0, 1);
			json = json.substring(1);

			if (!isInYinHao
					&& (token.equals(":") || token.equals("{")
							|| token.equals("}") || token.equals("[")
							|| token.equals("]") || token.equals(","))) {
				if (buf.toString().trim().length() == 0) {
					buf.append(token);
				}

				break;
			}

			if (token.equals("\\")) {
				buf.append(token);
				buf.append(json.substring(0, 1));
				json = json.substring(1);
				continue;
			}
			if (token.equals("\"")) {
				buf.append(token);
				if (isInYinHao) {
					break;
				} else {
					isInYinHao = true;
					continue;
				}
			}
			buf.append(token);
		}
		return buf.toString();
	}

	private static void doFill(StringBuilder buf, int count) {
		buf.append("\n");
	}

	public static void main(String[] args) {
		String json = "{\"result\":{\"userevent\":{\"notice\":9,\"message\":0,\"chorusinvite\":0,\"followsing\":0,\"compversion\":0,\"fannum\":0,\"feednum\":4},\"user\":{\"email\":\"kernel001@test.com\",\"token\":\"4ab4e5f636498248fe19e816f10dd55a\",\"location\":\"test\",\"signature\":\"test\",\"status\":1,\"lastactive\":\"2014-02-28 16:20:22\",\"authusers\":[{\"uid\":\"kernel001\",\"sns\":\"twitter\",\"original\":true,\"accesstoken\":\"kasjdfljasdfwekrjasdfskdhfkl\",\"authtime\":\"2014-12-12 00:00:00\",\"userid\":43},{\"uid\":\"123531\",\"sns\":\"123456\",\"original\":false,\"accesstoken\":\"ERFDFTSWDSDSDSDSD\",\"userid\":43}],\"userlevel\":{\"richlevel\":0,\"richlevelname\":\"\",\"nextrichlevel\":0,\"starlevel\":0,\"starlevelname\":\"\",\"nextstarlevel\":0,\"cost\":0,\"pop\":0,\"userid\":43,\"partcost\":0},\"userid\":43,\"nickname\":\"test\",\"createtime\":\"2014-02-12 16:03:47\",\"userstatistic\":{\"friendnum\":0,\"fannum\":0,\"giftnum\":0,\"singnum\":2,\"chorusnum\":2,\"repostnum\":1,\"focusnum\":1,\"userstatisticid\":43}},\"errorcode\":\"ok\"}}";
		System.out.println(formatJson(json));
	}
}
