package com.resourcetracker.tools.sort;

import com.resourcetracker.entity.ConfigEntity;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.springframework.stereotype.Service;

import java.io.ObjectInputFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Implements comparator used for sorting array of addresses
 *
 * @author YarikRevich
 */
@Service
public class RequestSortService {
     final static Logger logger = LogManager.getLogger(RequestSortService.class);

     class RequestSortComparator implements Comparator<ConfigEntity.Request> {
		 @Override
		 public int compare(ConfigEntity.Request o1, ConfigEntity.Request o2) {
			 Pattern pattern = Pattern.compile("\\d");
			 Matcher o1Matcher = pattern.matcher(o1.tag);
			 Matcher o2Matcher = pattern.matcher(o2.tag);

			 boolean o1Match = o1Matcher.matches();
			 boolean o2Match = o2Matcher.matches();
			 if (o1Match && o2Match){
				return Integer.parseInt(o1Matcher.group()) > Integer.parseInt(o2Matcher.group()) ? 0 : 1;
			 } else if (o1Match) {
				 return 1;
			 } else {
				 return 0;
			 }
		 }
	 };

     public void sort(ConfigEntity[] src) {
		 for (ConfigEntity configEntity : src){
			 Collections.sort(configEntity.requests, new RequestSortComparator());
		 }
     }
}
