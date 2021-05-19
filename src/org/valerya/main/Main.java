package org.valerya.main;

import org.valerya.data.Citizen;
import org.valerya.data.Domain;
import org.valerya.utils.ObjectHelper;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {
    
        /**************************************************************************/
        
        final int dice1Faces = 6;
        final int dice2Faces = 6;
        
        final Map<Integer, Integer> tossesProbability = new HashMap<>(dice1Faces + dice2Faces);
        
        IntStream.range(1, dice1Faces+1)
                .forEach(i -> IntStream.range(1, dice2Faces+1)
                        .forEach(j -> {
                            tossesProbability.merge(i, 1, Integer::sum);
                            tossesProbability.merge(j, 1, Integer::sum);
                            tossesProbability.merge(i+j, 1, Integer::sum);
                        }));
    
        System.out.println(tossesProbability);
    
        final Map<Integer, Integer> tossesProbability100 = IntStream.range(1, tossesProbability.size()+1)
                .boxed()
                .collect(Collectors.toMap(Function.identity(), i -> 100 * tossesProbability.get(i) / (dice1Faces*dice2Faces)));
    
        System.out.println(tossesProbability100);
        
        /**************************************************************************/
        
        Object[] objects = new Object[] {};
        System.out.println(ObjectHelper.equals(objects) + ": " + Arrays.toString(objects));
    
        objects = new Object[] {1, true, null, "ok"};
        System.out.println(ObjectHelper.equals(objects) + ": " + Arrays.toString(objects));
    
        objects = new Object[] {1, 1, 1};
        System.out.println(ObjectHelper.equals(objects) + ": " + Arrays.toString(objects));
    
        objects = new Object[] {1, false, 2};
        System.out.println(ObjectHelper.equals(objects) + ": " + Arrays.toString(objects));
        
        objects = new Object[] {Citizen.class, null, Citizen.class};
        System.out.println(ObjectHelper.equals(objects) + ": " + Arrays.toString(objects));
    
        objects = new Object[] {Domain.class, Domain.class, Domain.class};
        System.out.println(ObjectHelper.equals(objects) + ": " + Arrays.toString(objects));
    
        /**************************************************************************/
    }

}
