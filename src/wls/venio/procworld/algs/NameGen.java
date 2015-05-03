package wls.venio.procworld.algs;

public class NameGen{
	private static final String templates[]={
		"XAX",
		"AXA",
		"XA",
		"AX",
	};
	private static final char cons[]={
		'r',
		't',
		's',
		'd',
		'f',
		'g',
		'h',
		'k',
		'l',
		'c',
		'v',
		'n',
		'm',
	};
	private static int consFreq[]={
		2,	//r
		2,	//t
		2,	//s
		2,	//d
		1,	//f
		1,	//g
		1,	//h
		1,	//k
		2,	//l
		2,	//c
		1,	//v
		2,	//n
		1	//m
	};
	private static final char vow[]={
		'a',
		'e',
		'i',
		'o',
		'y'
	};
	private static final int vowFreq[]={
		2,	//a
		2,	//e
		2,	//i
		1,	//o
		1	//y
	};
	
	public static String generateName(){
		String str="";
		
		//TODO Name generation
		
		return str;
	}
}
