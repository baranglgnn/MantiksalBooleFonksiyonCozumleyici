import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class _21010310028_BooleMain {

	public static void main(String[] args) throws FileNotFoundException {
		Scanner read = new Scanner(new File("boole.txt"));

		ArrayList<_21010310028_Veriable> veriableList = new ArrayList<_21010310028_Veriable>();
		ArrayList<_21010310028_Term> termList = new ArrayList<_21010310028_Term>();
		String functionName ;
		
		LinkedHashMap<boolean[], Boolean> truthTableLinkedHashMap = new LinkedHashMap<boolean[],Boolean>();
		
		boolean truthTable [][] = new boolean [16][4];

		String s = read.nextLine().toString();

		String word[] = s.split("=");

		functionName = word[0];
		
		String inFunction = word[1].trim();

		String term[] = inFunction.split("\\+");

		veriableList = returnedVeriableList(inFunction);
		termList = returnedTermList(term);

		truthTable = createdTruthTable(veriableList);
		
		for(int i = 0 ; i<truthTable.length ; i++) {
			boolean control =functionResult(truthTable[i], termList, veriableList);
			truthTableLinkedHashMap.put(truthTable[i], control);
		}		
		
		System.out.println("boole.txt dosyası okudu");
		truthTablePrint(truthTableLinkedHashMap ,veriableList ,functionName);
		
		System.out.println();
		System.out.println("Fonksiyon değerleri : ");
		System.out.println();
		
		calculateMinterm(truthTableLinkedHashMap, veriableList,functionName);
		calculateMaxterm(truthTableLinkedHashMap, veriableList,functionName);
		
	}

	public static ArrayList<_21010310028_Term> returnedTermList(String inTerm[]) {

		ArrayList<_21010310028_Term> returnedTerm = new ArrayList<_21010310028_Term>();

		for (String t : inTerm) {
			_21010310028_Term term = new _21010310028_Term(t);
			returnedTerm.add(term);
		}

		return returnedTerm;
	}

	public static ArrayList<_21010310028_Veriable> returnedVeriableList(String inFunction) {

		ArrayList<_21010310028_Veriable> returnedVeriableList = new ArrayList<_21010310028_Veriable>();

		Set<String> set = new TreeSet<String>();

		for (int i = 0; i < inFunction.length(); i++) {
			char c = inFunction.charAt(i);
			String cS = String.valueOf(c);

			if (cS.equals("’") || cS.equals("+") || cS.equals(" ")) {

			} else {
				set.add(cS);
			}
		}

		for (String vs : set) {
			_21010310028_Veriable veriable = new _21010310028_Veriable(vs ,vs+"’");
			returnedVeriableList.add(veriable);
		}

		return returnedVeriableList;
	}

	public static boolean[][] createdTruthTable(ArrayList<_21010310028_Veriable> veriablesList) {

		boolean[][] table = new boolean[16][4];

		for (int row = 0; row < 16; row++) {
			int number = row;
			for (int i = 3; i >=0; i--) {
				table[row][i] = (number % 2) == 1;
				number /= 2;
			}
		}

		
		return table;
	}

	public static boolean termResult(boolean[] inTableRow , String inTerm , ArrayList<_21010310028_Veriable> inVeriableList) {
				
		boolean result = true ;
		
		for(int i = 0 ; i<inTerm.length() ; i++) {
			
			
			char carryChar = inTerm.charAt(i);
			String carryString = String.valueOf(carryChar);
			
			if(!carryString.equals("’")) {
				
				
				int whichVeriable = 0 ;
				for(int l = 0 ; l<inVeriableList.size() ; l++) {
					if(inVeriableList.get(l).getDegisken().equals(carryString)) {
						
						whichVeriable = l ;
						
					}
				}
				boolean veriableValue =  inTableRow[whichVeriable];
				
				
				if(i+1 <inTerm.length()) {
					String nextCarryString = String.valueOf(inTerm.charAt(i+1));
			
					if(nextCarryString.equals("’")) {
						
						result = result && !veriableValue;
						
					}else {
						
						result = result && veriableValue;

					}
					
				}else {					

					result = result && veriableValue;
					
				}	
			}
		}
			 
		return result ;
	}
	
	public static boolean functionResult(boolean[] inTableRow , ArrayList<_21010310028_Term> termList , ArrayList<_21010310028_Veriable> inVeriableList) {
		
		boolean functionResult = false ;
		
		for(_21010310028_Term inTerm : termList) {
			 functionResult = functionResult || termResult(inTableRow, inTerm.getTerm(), inVeriableList);
		}
		return functionResult ;
	}

	public static void truthTablePrint(Map<boolean[],Boolean> inTruthTableMap , ArrayList<_21010310028_Veriable> veriableList , String functionName) {
		
		System.out.println("Doğruluk tablosu : ");
		System.out.println();
		
		for(_21010310028_Veriable ver : veriableList) {
			System.out.print(ver.getDegisken() + "  ");
		}

		System.out.print("  "+functionName);
		System.out.println();
		Set<boolean[]> tableVeriables = inTruthTableMap.keySet();
		
		for(boolean[] degiskenSatir : tableVeriables) {
			for(boolean degisken : degiskenSatir) {
				if(degisken) {
					System.out.print("1  ");
				}else {
					System.out.print("0  ");
				}
				
			}
		if(inTruthTableMap.get(degiskenSatir)) {
			System.out.print("  1  ");
		}else {
			System.out.print("  0  ");
		}
		System.out.println();
			
		}	
	}

	public static void calculateMinterm(Map<boolean[],Boolean> inTruthTableMap , ArrayList<_21010310028_Veriable> veriableList , String functionName) {
		
		String minterm = "";
		String mintermShort = " Σ(";
		
		Set<boolean[]> tableVeriables = inTruthTableMap.keySet();
		
		int count = 0 ;
		for(boolean[] tableRow: tableVeriables) {
			
			boolean functionValue = inTruthTableMap.get(tableRow);
			
			if(functionValue) {
				
				mintermShort += count+"," ;
				
				
				for(int i = 0 ; i<tableRow.length ; i++) {
					
					boolean veriableValue = tableRow[i];
					_21010310028_Veriable minVeriable = veriableList.get(i);
					if(veriableValue) {
						minterm += minVeriable.getDegisken();
					}else {
						minterm += minVeriable.getTumleyen() ;

					}
				}
				
				minterm += " + ";
			}
			
			count ++ ;
		}
		
		minterm =  minterm.substring(0 ,minterm.length()-2);
		mintermShort = mintermShort.substring(0,mintermShort.length()-1).trim();
		System.out.println(functionName + " = " + mintermShort+")");
		System.out.println(functionName + " = " + minterm);
			
	}
	
	public static void calculateMaxterm(Map<boolean[],Boolean> inTruthTableMap , ArrayList<_21010310028_Veriable> veriableList,String functionName) {
		
		String maxterm = "";
		String maxtermShort = "∏(";
		
		Set<boolean[]> tableVeriables = inTruthTableMap.keySet();
		
		int count = 0 ;
		for(boolean[] tableRow: tableVeriables) {
			
			boolean functionValue = inTruthTableMap.get(tableRow);
			
			if(!functionValue) {
				
				maxtermShort += count + ",";
				
				maxterm += "(";
				for(int i = 0 ; i<tableRow.length ; i++) {
					
					
					boolean veriableValue = tableRow[i];
					_21010310028_Veriable minVeriable = veriableList.get(i);
					
					if(!veriableValue) {
						maxterm += minVeriable.getDegisken() +" + ";
					}else {
						maxterm += minVeriable.getTumleyen() + " + ";

					}
				}
				
				maxterm = maxterm.substring(0,maxterm.length()-3);
				maxterm += ")";
				
				maxterm += ".";
				
				
				
			}	
			count ++ ;
		}
		
		maxterm = maxterm.substring(0,maxterm.length()-1);
		maxtermShort = maxtermShort.substring(0,maxtermShort.length()-1).trim();
		System.out.println(functionName + " = " + maxtermShort+")");
		System.out.println(functionName + " = " + maxterm);
			
	}
}
