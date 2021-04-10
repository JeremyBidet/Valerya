package org.valerya.main;

import org.valerya.core.Resource;
import org.valerya.data.*;
import org.valerya.utils.parser.DataParser;

import java.io.IOException;
import java.util.Arrays;

public class Main {
	
	public static void main(String[] args) {

		try {
			
			DataParser.init();
			System.out.println(Citizen.citizens);
			System.out.println(Domain.domains);
			System.out.println(Monster.monsters);
			System.out.println(Duke.dukes);
			System.out.println(MonsterSet.monsterSets);
			
		} catch (IOException e) {
			e.printStackTrace();
		}

		int r1 = Resource.CITIZEN;
		int r2 = Resource.SOLDIER;

		System.out.println(r1);
		System.out.println(r2);
		System.out.println(Resource.CITIZEN | Resource.SOLDIER);
		System.out.println(r1 | r2);
		System.out.println(r1 & r2);
		System.out.println(Arrays.toString(Resource.names(144)));
		System.out.println(Arrays.toString(Resource.names(Resource.CITIZEN | Resource.SOLDIER)));
		System.out.println(Resource.value("CITIZEN", "SOLDIER"));

	}
	
}
