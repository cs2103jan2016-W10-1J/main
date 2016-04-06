# A0115449Aunused
###### \com\Main\OldSearcherByKeyword.java
``` java

import java.util.ArrayList;

public class OldSearcherByKeyword implements Commander {
private String keyWord;
private ArrayList<Task> TaskList;
private ArrayList<Task> searchResultList= new ArrayList<Task>();

public void SearcherByKeyword(String parsedUserInput, ArrayList<Task> TaskList){
	//The 1st element in the string array is the keyword for search
	keyWord = parsedUserInput;
	this.TaskList = TaskList;
}

@Override
public String execute() {
	keyWord = keyWord.trim();
	if (TaskList.size() == 0) {
		return "TodoList is empty";
	} else {
		for (Task currentTask : TaskList){
			if (currentTask.getTaskName().contains(keyWord)){
				searchResultList.add(currentTask);
			}
			else {
				if (currentTask.getLocation().contains(keyWord)){
					searchResultList.add(currentTask);
				}
				else {
					if (currentTask.getNotification().contains(keyWord)){
						searchResultList.add(currentTask);
					}
					else {
						if (currentTask.getTag().contains(keyWord)){
							searchResultList.add(currentTask);
						}
					}
				}
			}

		}
		ConverterToString output = new ConverterToString(searchResultList);
		return output.convert();
	}

}

@Override
public String undo() {
	// TODO Auto-generated method stub
	return null;
}
}
```
