package kr.lesaautomaton.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

/**
 * List의 Paging 구현을 용이하게 하는 Collection Bean
 * 
 * @author Dongsu Lee
 * 
 */
public class ListChunk implements Serializable, List {

	/** 전체 데이터 수 */
	private int totalCount = 0;
	/** 결과 List */
	private ArrayList dataList = new ArrayList();
	/** 각종 정보를 저장하는 Map */
	private HashMap values = new HashMap();
	/** 만약 rownum과 같은 컬럼이 없을 경우 이를 이용하여 번호를 부여할 수 있다. */
	private int rownum = 0;
	private boolean isDesc = false;
	
	private int pageSize = 10;
	
	/**
	 * Constructor
	 * @param count
	 * @param data
	 */
	public ListChunk(int count, ArrayList data) {
		this.totalCount = count;
		if(data!=null) this.dataList   = data;
	}
	
	/**
	 * Default Constructor
	 */
	public ListChunk() {
		super();
	}

	/**
	 * 증가/감소 여부(isDesc)에 따라서 rownum을 가져올때
	 * 값을 1씩 증가/감소 시켜서 가져온다. 
	 * 
	 * @return int
	 */
	public int getRownum() {
		if(isDesc) return rownum--;
		return rownum++;
	}

	/**
	 * Sets the rownum. (Default로 DESC 정렬을 이용한다.)
	 * 
	 * @param rownum The rownum to set
	 */
	public void setRownum(int rownum) {
		setRownum(rownum, true);
	}

	/**
	 * Sets the rownum.
	 * 
	 * @param rownum 시작번호
	 * @param isDesc 번호의 증가/감소 여부
	 */
	public void setRownum(int rownum, boolean isDesc) {
		this.rownum = rownum;
		this.isDesc = isDesc;
	}
	
	/**
	 * return totalCount
	 */
	public int getTotalCount() {
		return totalCount;
	}

	/**
	 * set totalCount
	 */
	public void setTotalCount(int count) {
		this.totalCount = count;
	}
	
	/**
	 * return dataList
	 */
	public ArrayList getDataList() {
		return dataList;
	}


	/**
	 * set dataList
	 */
	public void setDataList(ArrayList data) {
		if(data==null) return;
		this.dataList = data;
	}

	/**
	 * get data count
	 */
	public int getDataCount() {
		return this.size();
	}

	/**
	 * return user define value
	 */
	public Object getValue(Object objKey) {
		if(values==null) return null;
		return values.get(objKey);
	}

	/**
	 * set user define value
	 */
	public void setValue(Object objKey, Object value) {
		if(values==null) return;
		this.values.put(objKey, value);
	}

	/**
	 * return all user define values
	 */
	public HashMap getAllValues() {
		return this.values;
	}

	/* (non-Javadoc)
	 * @see java.util.List#size()
	 */
	public int size() {
		return dataList.size();
	}

	/* (non-Javadoc)
	 * @see java.util.List#isEmpty()
	 */
	public boolean isEmpty() {
		return dataList.isEmpty();
	}

	/* (non-Javadoc)
	 * @see java.util.List#contains(java.lang.Object)
	 */
	public boolean contains(Object o) {
		return dataList.contains(o);
	}

	/* (non-Javadoc)
	 * @see java.util.List#iterator()
	 */
	public Iterator iterator() {
		return dataList.iterator();
	}

	/* (non-Javadoc)
	 * @see java.util.List#toArray()
	 */
	public Object[] toArray() {
		return dataList.toArray();
	}

	/* (non-Javadoc)
	 * @see java.util.List#toArray(java.lang.Object[])
	 */
	public Object[] toArray(Object[] a) {
		return dataList.toArray(a);
	}

	/* (non-Javadoc)
	 * @see java.util.List#add(java.lang.Object)
	 */
	public boolean add(Object o) {
		return dataList.add(o);
	}

	/* (non-Javadoc)
	 * @see java.util.List#remove(java.lang.Object)
	 */
	public boolean remove(Object o) {
		return dataList.remove(o);
	}

	/* (non-Javadoc)
	 * @see java.util.List#containsAll(java.util.Collection)
	 */
	public boolean containsAll(Collection c) {
		return dataList.containsAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.List#addAll(java.util.Collection)
	 */
	public boolean addAll(Collection c) {
		return dataList.addAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.List#addAll(int, java.util.Collection)
	 */
	public boolean addAll(int index, Collection c) {
		return dataList.addAll(index, c);
	}

	/* (non-Javadoc)
	 * @see java.util.List#removeAll(java.util.Collection)
	 */
	public boolean removeAll(Collection c) {
		return dataList.removeAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.List#retainAll(java.util.Collection)
	 */
	public boolean retainAll(Collection c) {
		return dataList.retainAll(c);
	}

	/* (non-Javadoc)
	 * @see java.util.List#clear()
	 */
	public void clear() {
		this.dataList.clear();
	}

	/* (non-Javadoc)
	 * @see java.util.List#get(int)
	 */
	public Object get(int index) {
		return dataList.get(index);
	}

	/* (non-Javadoc)
	 * @see java.util.List#set(int, java.lang.Object)
	 */
	public Object set(int index, Object element) {
		return dataList.set(index, element);
	}

	/* (non-Javadoc)
	 * @see java.util.List#add(int, java.lang.Object)
	 */
	public void add(int index, Object element) {
		this.dataList.add(index, element);
	}

	/* (non-Javadoc)
	 * @see java.util.List#remove(int)
	 */
	public Object remove(int index) {
		return this.dataList.remove(index);
	}

	/* (non-Javadoc)
	 * @see java.util.List#indexOf(java.lang.Object)
	 */
	public int indexOf(Object o) {
		return dataList.indexOf(o);
	}

	/* (non-Javadoc)
	 * @see java.util.List#lastIndexOf(java.lang.Object)
	 */
	public int lastIndexOf(Object o) {
		return dataList.lastIndexOf(o);
	}

	/* (non-Javadoc)
	 * @see java.util.List#listIterator()
	 */
	public ListIterator listIterator() {
		return dataList.listIterator();
	}

	/* (non-Javadoc)
	 * @see java.util.List#listIterator(int)
	 */
	public ListIterator listIterator(int index) {
		return dataList.listIterator(index);
	}

	/* (non-Javadoc)
	 * @see java.util.List#subList(int, int)
	 */
	public List subList(int fromIndex, int toIndex) {
		return dataList.subList(fromIndex, toIndex);
	}


	/**
	 * @return
	 */
	public int getPageSize() {
		return pageSize;
	}

	/**
	 * @param i
	 */
	public void setPageSize(int i) {
		pageSize = i;
	}

}
