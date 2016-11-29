package pu.hui.restauto.restassure;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class Datas {

	public List<Data> datas = new ArrayList<Data>();

	public void addItem(Data data) {
		datas.add(data);
	}

	public Iterator createIrerator() {
		return new DataIterator();

	}

	public class DataIterator implements Iterator {

		int position = 0;

		public boolean hasNext() {
			if (position > datas.size() - 1 || datas.get(position) == null) {
				return false;
			} else {
				return true;
			}
		}

		public Data[] next() {
			
			Data data = datas.get(position);
		    position ++;
		    return new Data[]{data};
		}

	}

}
