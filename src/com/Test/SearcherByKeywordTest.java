//@@author A0116764B
package com.Test;
import org.junit.Test;

import com.Main.SearcherByKeyword;

import static org.junit.Assert.*;

public class SearcherByKeywordTest {

	@Test
	public void toLowerCaseTest(){
		SearcherByKeyword search = new SearcherByKeyword();
		String searchTerm = "ThiS iS a LowErCaSe sTring";
		searchTerm = search.toLowerCase(searchTerm);
		assertEquals(searchTerm, "this is a lowercase string");
		
	}
}