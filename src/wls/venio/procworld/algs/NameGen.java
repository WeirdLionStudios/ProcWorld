package wls.venio.procworld.algs;

import java.util.Random;

public class NameGen{
	
	static Random rand;
	
	public static void setSeed(double s){
		rand=new Random((long)(s*1000000));
	}
	
	private static final String templates[]={
		"XAX",
		"AXA",
		"XA",
		"AX",
	};
	private static int templateFreq[]={
		2,	//XAX
		1,	//AXA
		2,	//XA
		2	//AX
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
	
	private static int getWeightedRandom(int max, int[] w){
		int curIndx=1, curSum=w[0];
		int total=0;
		for(int i=0;i<max;i++)
			total+=w[i];
		int r=rand.nextInt(total);
		while(curSum<r&&curIndx<max-1){
			curSum+=w[curIndx++];
		}
		return curIndx;
	}
	
	public static String generateName(){
		String str="";
		
		int len=rand.nextInt(2)+2;
		for(int i=0;i<len;i++){
			int tInd=getWeightedRandom(templates.length, templateFreq);
			for(int j=0;j<templates[tInd].length();j++){
				if(templates[tInd].charAt(j)=='X')
					str+=cons[getWeightedRandom(cons.length, consFreq)];
				else
					str+=vow[getWeightedRandom(vow.length, vowFreq)];
			}
		}
		
		str=Character.toUpperCase(str.charAt(0))+str.substring(1);
		
		return str;
	}
}
