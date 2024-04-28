package org.example.insurancemanagementapplication.Utility;

import javafx.collections.ObservableList;

import java.util.List;
import java.util.ListIterator;

/**
 * @author Luong Thanh Trung
 * @version ${}
 * @created 29/04/2024 06:07
 * @project InsuranceManagementTeamProject
 */
public class IteratorUtility {
    public static <T> void addToObservableList(List<T> sourceList, ObservableList<T> destinationList){
        ListIterator<T> listIterator = sourceList.listIterator();
        while (listIterator.hasNext()){
            destinationList.add(listIterator.next());
        }
    }
}
