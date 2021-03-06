package se.kth.jpf_visual;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;


import se.kth.tracedata.Left;
import se.kth.tracedata.LockInstruction;
import se.kth.tracedata.Pair;
import se.kth.tracedata.ChoiceGenerator;
import se.kth.tracedata.ClassInfo;
import se.kth.tracedata.FieldInstruction;
import se.kth.tracedata.Instruction;
import se.kth.tracedata.MethodInfo;
import se.kth.tracedata.Path;
import se.kth.tracedata.Step;
import se.kth.tracedata.ThreadChoiceFromSet;
import se.kth.tracedata.ThreadInfo;
import se.kth.tracedata.Transition;
import se.kth.tracedata.VirtualInvocation;
import se.kth.tracedata.JVMInvokeInstruction;
import se.kth.tracedata.JVMReturnInstruction;



//methods of ThreadChoiceFromSet is created inside the ChoiceGenerator along with condition checking method for cg instance of ThreadChoiceFromSet


public class TraceData {

	private int numOfThreads = -1;
	private List<String> threadNames = null;
	
	private String lastLockName= null;
	private Path path;
	
	private List<Integer> threadId = null;
	
	

	private List<Pair<Integer, Integer>> group = new ArrayList<>();

	private Set<String> fieldNames = new HashSet<>();
	private Set<Pair<Integer, Integer>> waitSet = new HashSet<>();
	private Map<String, Set<Pair<Integer, Integer>>> lockTable = new HashMap<>();
	private Set<Pair<Integer, Integer>> threadStartSet = new HashSet<>();
	private Map<Integer, TextLineList> lineTable = new HashMap<>();
	private Set<String> lockMethodName = new HashSet<>();
	private Map<String, Set<Pair<Integer, Integer>>> classFieldMap = new HashMap<>();
	private Map<String, Set<Pair<Integer, Integer>>> classMethodMap = new HashMap<>();
	private Map<Pair<Integer, Integer>, List<Pair<Integer, String>>> threadStateMap = new HashMap<>();
	private Map<String, Set<String>> classFieldNameMap = new HashMap<>();
	private Map<String, Set<String>> classMethodNameMap = new HashMap<>();

	public TraceData(Path path) {
		this.path = path;
		// group the transition range
		group = new ArrayList<>();
		threadNames = new ArrayList<>();
		numOfThreads = -1;
		threadId = new ArrayList<>();
		if (path.size() == 0) {
			return; // nothing to publish
		}

		
		// group actions by thread
		firstPass();

		// register actions that can be visualized
		secondPass();

		// special step: process synchronized methods
		// to ensure they are treated as if having monitorenter/exit
		// at the beginning and end of the method
		processSynchronizedMethods();
	}


	private void firstPass() {
		int currTran = 0;
		int prevThread = -1;
		int start = -1;
		
	
		for(Transition t: path) {
			String choiId =t.getChoiceGenerator().getId();
			long threadtransitionId = t.getThreadInfo().getId();
			
			
				
			int currThread = t.getThreadIndex();
			
			//Logic to add the unique thread Name
			//if ((threadNames.size() == currThread) || !(threadId.contains(currThread))) {
			if ( !(threadId.contains(currThread)) && currThread > prevThread) {
				threadNames.add(t.getThreadInfo().getName());
				threadId.add(currThread);
			}
			if (currTran == 0) {
				start = 0;
			}
			if (currTran > 0 && currThread != prevThread) {
				group.add(new Pair<Integer, Integer>(start, currTran - 1));
				start = currTran;
			}

			if (currTran == path.size() - 1) {
				group.add(new Pair<Integer, Integer>(start, currTran));
			}

			prevThread = currThread;
			currTran++;
			numOfThreads = Math.max(numOfThreads, currThread);
		}
		//numOfThreads++;
		numOfThreads=threadId.size();
		
	}

	private void secondPass() {
		
		
		// second pass of the path
		int prevThreadIdx = 0;
		
		for (int pi = 0; pi < group.size(); pi++) {
			Pair<Integer, Integer> p = group.get(pi);
			int from = p._1;
			int to = p._2;
			int height = 0;
			StringBuilder tempStr = new StringBuilder();

			ArrayList<TextLine> lineList = new ArrayList<>();
			TextLineList txtLinelist = new TextLineList(lineList);
			lineTable.put(pi, txtLinelist);
			/*for (Integer i: lineTable.keySet()){

	            int key =i;
	            String value = example.get(name).toString();  
	            System.out.println(key + " " + value);  


	}*/

			boolean isFirst = true;
			for (int i = from; i <= to; i++) {
				Transition transition = path.get(i);
				String lastLine = null;
				
				int nNoSrc = 0;
				ChoiceGenerator<?> cg = transition.getChoiceGenerator();
				//if (cg instanceof ThreadChoiceFromSet) {
				//if condition is checked with the isInstaceofThreadChoiceFromSet() method in choicegeneraotr for jpf error trace
				//as the cg is of type jpf as we are using adapters
				 //System.out.println("Choice Id  "+cg.getId()+"ThreadId "+transition.getThreadInfo().getId()+"\n");
				if (cg.isInstaceofThreadChoiceFromSet() || cg instanceof ThreadChoiceFromSet) {
					if(transition.getThreadInfo().getLastLockName() == "JVM") {	
						prevThreadIdx = transition.getThreadIndex();
						
					}
					
						ThreadInfo ti = transition.getThreadInfo();
						processChoiceGenerator(cg, prevThreadIdx, pi, height, ti,"");	
						
					
				}

				tempStr.append(cg + "\n");
				TextLine txt = new TextLine(cg.toString(), true, false, transition, pi, height);
				lineList.add(txt);

				height++;
				TextLine txtSrc = null;
				for (int si = 0; si < transition.getStepCount(); si++) {
					Step s = transition.getStep(si);
					//System.out.println("Choice Id"+s.getCg().getId()+" threadId "+transition.getThreadInfo().getId()+" Location "+s.getLineString()+"Location string "+s.getLocationString()+"\n");
					String line = s.getLineString();
					
					if (line != null) {
						String src = line.replaceAll("/\\*.*?\\*/", "").replaceAll("//.*$", "")
								.replaceAll("/\\*.*$", "").replaceAll("^.*?\\*/", "").replaceAll("\\*.*$", "").trim();

						if (!line.equals(lastLine) && src.length() > 0) {
							if (nNoSrc > 0) {
								String noSrc = " [" + nNoSrc + " insn w/o sources]";
								tempStr.append(noSrc + "\n");
								
								txtSrc = new TextLine(noSrc, false, false, transition, pi, height);
								lineList.add(txtSrc);
								height++;
							}
							tempStr.append(" ");
							tempStr.append(Left.format(s.getLocationString(), 20));
							tempStr.append(": ");
							tempStr.append(src + "\n");

							txtSrc = new TextLine(src, false, true, transition, pi, height);
							txtSrc.setStartStep(si);
							if (isFirst) {
								isFirst = false;
								txtSrc.setFirst();
							}
							lineList.add(txtSrc);
							
							
							height++;
							nNoSrc = 0;
						}
					} else { // no source
						nNoSrc++;
					}
					
					if(transition.getThreadInfo().getLastLockName() == "JVM") {	
						
							if(s.getCg().getId()=="START"||s.getCg().getId()=="JOIN")
							{
								
							ThreadInfo ti = transition.getThreadInfo();
							processChoiceGenerator(s.getCg(), prevThreadIdx, pi, height, ti,line);
							}
							/*else if((cg.getId()=="Running")&&(s.getCg().getId()=="LOCK"||s.getCg().getId()=="RELEASE"||s.getCg().getId()=="WAIT"||s.getCg().getId()=="TERMINATE"))
							{
								ThreadInfo ti = transition.getThreadInfo();
								processChoiceGenerator(s.getCg(), prevThreadIdx, pi, height, ti,line);
							}*/
						
					}
					
					lastLine = line;

					if (line != null && txtSrc != null) {
						txtSrc.setEndStep(si);
					}

					Instruction insn = s.getInstruction();
					MethodInfo mi = insn.getMethodInfo();
					ThreadInfo ti = transition.getThreadInfo();
					

					loadSynchronizedMethod(line, mi);

					loadWaitNotify(line, insn, pi, height);

					loadLockUnlock(line, insn, mi, ti, pi, height);

					loadFields(line, insn, txtSrc);

					loadMethods(line, insn, txtSrc);
				}
				prevThreadIdx = transition.getThreadIndex();

				ThreadInfo ti = transition.getThreadInfo();
				// final transition wait
				if (pi == group.size() - 1 && i == to) {
					loadFinaWaitInFinalTransition(ti, pi, height);
				}
			}

			tempStr.deleteCharAt(tempStr.length() - 1);

			/**
			 * set last line
			 */
			int li = lineList.size() - 1;
			for (; li >= 0; li--) {
				if (lineList.get(li).isSrc()) {
					lineList.get(li).setLast();
					break;
				}
			}

			if (li == -1) {
				txtLinelist.setNoSrc(true);
			}

		}


	}

	
	private void processChoiceGenerator(ChoiceGenerator<?> cg, int prevThreadIdx, int pi, int height, ThreadInfo ti, String line) {
		// thread start/join highlight
	
		
		if ((cg.getId() == "START" || cg.getId() == "JOIN") && height>0 && ti.getLastLockName() != "JVM") {
			if (lineTable.get(pi).getTextLine(height - 1).isSrc()) {
				threadStartSet.add(new Pair<>(pi, height - 1));
			}
		
			
		}
		if(ti.getLastLockName() == "JVM" && (cg.getId() == "START" || cg.getId() == "JOIN")&& height>0 &&(line.contains("start")||line.contains("join")))
		{
			if (lineTable.get(pi).getTextLine(height - 1).isSrc()) {
				//System.out.println("Value: "+getTextLine(height - 1)+"\n");
				threadStartSet.add(new Pair<>(pi, height - 1));
			}
			return;
		}
		Pair<Integer, Integer> tmp = new Pair<>(pi, height);

		// thread state view
		// ROOT - main thread
		if (cg.getId() == "ROOT") {
			Pair<Integer, String> threadState = new Pair<>(ti.getId(), "ROOT");
			ArrayList<Pair<Integer, String>> list = new ArrayList<>();
			list.add(threadState);
			threadStateMap.put(tmp, list);
		}

		// TERMINATE
		if (cg.getId() == "TERMINATE") {
			Pair<Integer, String> threadState = new Pair<>(prevThreadIdx, "TERMINATE");
			ArrayList<Pair<Integer, String>> list = new ArrayList<>();
			list.add(threadState);
			threadStateMap.put(tmp, list);
		}

		// START
		if (cg.getId() == "START") {
			//we have created method getChoice() directly inside the choicegenerator so we can access it
			// with choicegenerator object we donot need casting 
			//int tid = ((ThreadChoiceFromSet) cg).getChoice(cg.getTotalNumberOfChoices() - 1).getId();
			int tid =0;
			if(cg.getTotalNumberOfChoices() == 0)
			{
				tid = ((ThreadChoiceFromSet) cg).getThreadId();
			}
			else
			{
				//we have created method getChoice() directly inside the choicegenerator so we can access it
				// with choicegenerator object we donot need casting  for jpf error trace
			
				tid =cg.getChoice(cg.getTotalNumberOfChoices() - 1).getId();
			}
			Pair<Integer, String> threadState = new Pair<>(tid, "START");

			ArrayList<Pair<Integer, String>> list = new ArrayList<>();
			list.add(threadState);
			threadStateMap.put(tmp, list);
		}

		// LOCK
		if (cg.getId() == "LOCK") {
			Pair<Integer, String> threadState = new Pair<>(prevThreadIdx, "LOCK");

			ArrayList<Pair<Integer, String>> list = new ArrayList<>();
			list.add(threadState);
			threadStateMap.put(tmp, list);
		}

		// WAIT
		if (cg.getId() == "WAIT") {
			Pair<Integer, String> threadState = new Pair<>(prevThreadIdx, "WAIT");

			ArrayList<Pair<Integer, String>> list = new ArrayList<>();
			list.add(threadState);
			threadStateMap.put(tmp, list);
		}

		// RELEASE
		if (cg.getId() == "RELEASE") {
			Pair<Integer, String> threadState = new Pair<>(prevThreadIdx, "RELEASE");
			Pair<Integer, String> threadState2 = new Pair<>(ti.getId(), "RELEASE");

			ArrayList<Pair<Integer, String>> list = new ArrayList<>();
			list.add(threadState);
			list.add(threadState2);
			threadStateMap.put(tmp, list);
		}

	}

	private void loadSynchronizedMethod(String line, MethodInfo mi) {
		if (line != null && mi.isSynchronized()) {
			ClassInfo mci = mi.getClassInfo();
			String mName = mi.getUniqueName();
			if (mci != null && mi.getUniqueName() != null && mName != null && !mName.contains("<clinit>")) {
				lockMethodName.add(mci.getName() + "." + mName);
			}
		}
	}


	//check the insn instanceof VirtualInvocation using method isInstanceofVirtualInv()  
	
	private void loadWaitNotify(String line, Instruction insn, int pi, int height) {
		if (line != null && (insn.isInstanceofVirtualInv() || insn instanceof VirtualInvocation )) {
			
			String insnStr = null;
			if(insn.isInstanceofVirtualInv())
			{
				insnStr=insn.toString();
			}
			else if(insn instanceof VirtualInvocation )
			{
				//As the instruction don't contain the information of the wait,notify directly 
				//hence we have access that information from the signature parameter of the methodInfo
				insnStr =insn.getMethodInfo().getUniqueName();
				
			}
			if (insnStr.contains("java.lang.Object.wait(") || insnStr.contains("java.lang.Object.notify(")
					|| insnStr.contains("java.lang.Object.notifyAll(")) {
				waitSet.add(new Pair<>(pi, height - 1));
			}
			
		}
	}
/*
 *  created the methods of LockInstruction inside the Instruction adapter and checking the insn is the instaceof condition using 
 *	 function isInstanceofLockIns. Hence methods are directly calling with the help of Instruction object.
 * 
 **/
		
	private void loadLockUnlock(String line, Instruction insn, MethodInfo mi, ThreadInfo ti, int pi, int height) {
		
		//if field is lock then i have to LockInstruction
		
		if (line != null && (insn.isInstanceofLockIns() || insn instanceof LockInstruction))
		{
			/*
			 * ElementInfo is used to get the name of the last lock that was used by a thread.
			 * Hence we removed the getElementInfo in ThreadInfo adapter and we have created another method getNameOfLastLock
			 * which taking lastlocreference.
			 * The function internally calling ElementInfo and converting it to the string.
			 * Returning fieldName 
			 *
			 */
			int lastLock = 0;
			String fieldName =null;
			
			if (insn.isInstanceofLockIns())
			{
				lastLock= insn.getLastLockRef();
				fieldName = ti.getNameOfLastLock(lastLock);
				
			}
			else if( insn instanceof LockInstruction)
			{
				lastLock = ((LockInstruction)insn).getLastLockRef();
				fieldName = ti.getNameOfLastLock(lastLock);
				if(fieldName=="JVM") {
					
					fieldName=((LockInstruction)insn).getLastlockName();
				}
			}
			
			
			Pair<Integer, Integer> pair = new Pair<>(pi, height - 1);

			if (fieldNames.contains(fieldName)) {
				lockTable.get(fieldName).add(pair);
			} else {
				fieldNames.add(fieldName);
				Set<Pair<Integer, Integer>> newSet = new HashSet<>();
				newSet.add(pair);
				lockTable.put(fieldName, newSet);
			}
		}
		
		//if the method is lock then I have to use JVMReturnIns synchronization method
		//checking insn instanceof JVMReturnInstruction inside method isInstanceofJVMReturnIns() inside instruction adapter
		if (line != null && (insn.isInstanceofJVMReturnIns() || insn instanceof JVMReturnInstruction)) {
			String mName = mi.getFullName();
			String cName = mi.getClassName();
			//System.out.println("Class Name "+cName+"Method Naem"+mName+"\n");
			if (lockMethodName.contains(mName)) {
				Pair<Integer, Integer> pair = new Pair<>(pi, height - 1);
				if (fieldNames.contains(cName)) {
					lockTable.get(cName).add(pair);
				} else {
					fieldNames.add(cName);
					Set<Pair<Integer, Integer>> newSet = new HashSet<>();
					newSet.add(pair);
					lockTable.put(cName, newSet);
				}

			}
		}
		
	}

	private void loadFields(String line, Instruction insn, TextLine txtSrc) {
		//insn instanceof FieldInstruction is checking with the help of method  isInstanceofFieldIns() 
		//if (line != null && txtSrc != null && txtSrc.isSrc() && insn.isInstanceofFieldIns()) {
		
		
		if(line != null && txtSrc != null && txtSrc.isSrc() && (insn.isInstanceofFieldIns() || insn instanceof FieldInstruction)) {
			/*  as method directly created insde instruction adapter hence we can call it using insn object and the there no need of casting
			* String name = ((FieldInstruction) insn).getVariableId();
			* */
			String name = null;
			if(insn.isInstanceofFieldIns())
			{
				name= insn.getVariableId();
			}
			else if (insn instanceof FieldInstruction)
			{
				name = ((FieldInstruction) insn).getVariableId();
			}
			
			int dotPos = name.lastIndexOf(".");
			if (dotPos == 0 || dotPos == name.length() - 1) {
			} else {
				String clsName = name.substring(0, dotPos);
				String fmName = name.substring(dotPos + 1, name.length());
				if (classFieldNameMap.containsKey(clsName)) {
					classFieldNameMap.get(clsName).add(fmName);
				} else {
					Set<String> fields = new HashSet<>();
					fields.add(fmName);
					classFieldNameMap.put(clsName, fields);
				}
			}
		}
		
		
	}
	
	private void loadMethods(String line, Instruction insn, TextLine txtSrc) {
		
	
	//checkIJVMInvok is checking whether the ins is the instance of JVMInvokeInstruction internally inside instruction adapter		
	boolean	checkIJVMInvok = insn.isInstanceofJVMInvok();

		
		if (line != null && txtSrc != null && txtSrc.isSrc() && (checkIJVMInvok || insn instanceof JVMInvokeInstruction )) {
			/* instead of calling getInvokedMethodName() of JVMInvokeInstruction i have now changed it
			* I have created methods getInvokedMethodName() and getInvokedMethodClassName() inside instruction interface 
			*to remove the previous error of instanceof and casting.
			*
			*/ 
			String methodName=null;
			String clsName=null;
			if(checkIJVMInvok) {
				methodName = insn.getInvokedMethodName().replaceAll("\\(.*$", "");
				clsName = insn.getInvokedMethodClassName();
				
			}
			else if(insn instanceof JVMInvokeInstruction)
			{
				 methodName = ((JVMInvokeInstruction)insn).getInvokedMethodName().replaceAll("\\(.*$", "");
				 clsName = ((JVMInvokeInstruction)insn).getInvokedMethodClassName();
			}
				
			
			if (classMethodNameMap.containsKey(clsName)) {
				classMethodNameMap.get(clsName).add(methodName);
			} else {
				Set<String> methods = new HashSet<>();
				methods.add(methodName);
				classMethodNameMap.put(clsName, methods);
			}
		}

	}

	private void loadFinaWaitInFinalTransition(ThreadInfo ti, int pi, int height) {
		// final transition wait
		Pair<Integer, Integer> tmp = new Pair<>(pi, height - 1);
		if (ti.getStateName() == "WAITING" || ti.getStateName() == "TIMEOUT_WAITING") {
			Pair<Integer, String> threadState = new Pair<>(ti.getId(), "WAIT");
			ArrayList<Pair<Integer, String>> list = new ArrayList<>();
			list.add(threadState);
			threadStateMap.put(tmp, list);
		}

	}

	private void processSynchronizedMethods() {
		if (!lockMethodName.isEmpty()) {
			for (TextLineList list : lineTable.values()) {
				for (TextLine tl : list.getList()) {
					if (!tl.isSrc()) {
						continue;
					}
					processTextLineForSynchronizedMethods(tl);
				}
			}
		}
	}

	private void processTextLineForSynchronizedMethods(TextLine tl) {
		for (int si = tl.getStartStep(); si <= tl.getEndStep(); si++) {
			Step s = tl.getTransition().getStep(si);
			Instruction insn = s.getInstruction();
			
			/*
			if (insn instanceof VirtualInvocation) {
			this if condition using method isInstanceofVirtualInv() inside instruction. 
			 if it is a instace of that object then it will call the methods getInvokedMethodClassName() and getInvokedMethodName of instructions
			 * 
			 */
			
			String cName = null;
					String tmp = null;
				if (insn.isInstanceofVirtualInv() )
				{
					
						cName = insn.getInvokedMethodClassName();
						tmp = cName + "." + insn.getInvokedMethodName();
					
				}
				else if(insn instanceof VirtualInvocation) // modified as the getInvokedMethodClassName and getInvokedMethodName function are in the VirtualInvocation
				{
					cName = ((VirtualInvocation)insn).getInvokedMethodClassName();
					tmp = cName + "." + ((VirtualInvocation)insn).getInvokedMethodName();
					
				}
				
				Pair<Integer, Integer> pair = new Pair<>(tl.getGroupNum(), tl.getLineNum());

				if (lockMethodName.contains(tmp)) {
					if (fieldNames.contains(cName)) {
						lockTable.get(cName).add(pair);
					} else {
						fieldNames.add(cName);
						Set<Pair<Integer, Integer>> newSet = new HashSet<>();
						newSet.add(pair);
						lockTable.put(cName, newSet);
					}

				}
			}
		}
	

	public Set<Pair<Integer, Integer>> getClassField(String clsName, String fieldName) {
		String target = clsName + "." + fieldName;
		if (classFieldMap.containsKey(target)) {
			return classFieldMap.get(target);
		}

		Set<String> srcSet = new HashSet<>();
		Set<Pair<Integer, Integer>> targetSet = new HashSet<>();
		for (TextLineList list : lineTable.values()) {
			for (TextLine tl : list.getList()) {
				if (!tl.isSrc()) {
					continue;
				}
				processTextLineForClassField(tl, clsName, target, srcSet, targetSet);
			}
		}
		classFieldMap.put(target, targetSet);
		return targetSet;
	}

	private void processTextLineForClassField(TextLine tl, String clsName, String target, Set<String> srcSet,
			Set<Pair<Integer, Integer>> targetSet) {
		for (int si = tl.getStartStep(); si <= tl.getEndStep(); si++) {
			Step step = tl.getTransition().getStep(si);
			Instruction insn = step.getInstruction();
			String cName = insn.getMethodInfo().getClassInfo().getName();
			if (clsName.equals(cName) && srcSet.contains(insn.getFileLocation()))
			{
				targetSet.add(new Pair<Integer, Integer>(tl.getGroupNum(), tl.getLineNum()));
				break;
			} 
			//else if (insn instanceof FieldInstruction) {
			//if condition is checked using method isInstanceofFieldIns()
			else if (insn.isInstanceofFieldIns() || insn instanceof FieldInstruction) {
				/*
				//method is called directly using insn object as method is now created inside instreuction adapter itsel instade of FieldInstruction class
				//hence there is no need of seperate FieldInstruction class
				 * 
				 */
				if (insn.isInstanceofFieldIns() )
				{
					if (insn.getVariableId().equals(target)) {
						targetSet.add(new Pair<Integer, Integer>(tl.getGroupNum(), tl.getLineNum()));
						srcSet.add(insn.getFileLocation());
						break;
					}
				}
				else if(insn instanceof FieldInstruction)
				{
					if (((FieldInstruction)insn).getVariableId().equals(target)) {
						targetSet.add(new Pair<Integer, Integer>(tl.getGroupNum(), tl.getLineNum()));
						srcSet.add(((FieldInstruction)insn).getFileLocation());
						break;
					}
				}
			}
		}
	}

	// getters

	public Set<Pair<Integer, Integer>> getClassMethod(String clsName, String methodName) {
		String target = clsName + "." + methodName;
		if (classMethodMap.containsKey(target)) {
			return classMethodMap.get(target);
		}
		Set<Pair<Integer, Integer>> targetSet = new HashSet<>();
		for (TextLineList list : lineTable.values()) {
			Map<String, String> srcMap = new HashMap<>();
			for (TextLine tl : list.getList()) {
				if (!tl.isSrc()) {
					continue;
				}
				processTextLineForClassMethod(tl, srcMap, targetSet, clsName, methodName);
			}
		}
		classMethodMap.put(target, targetSet);
		return targetSet;
	}

	private void processTextLineForClassMethod(TextLine tl, Map<String, String> srcMap,
			Set<Pair<Integer, Integer>> targetSet, String clsName, String methodName) {
		for (int si = tl.getStartStep(); si <= tl.getEndStep(); si++) {
			Step step = tl.getTransition().getStep(si);
			Instruction insn = step.getInstruction();
			String cName = insn.getMethodInfo().getClassInfo().getName();
			if (cName.equals(srcMap.get(insn.getFileLocation()))) {
				targetSet.add(new Pair<Integer, Integer>(tl.getGroupNum(), tl.getLineNum()));
				break;
			} 
			//else if (insn instanceof JVMInvokeInstruction) {
			else if(insn.isInstanceofJVMInvok() || insn instanceof JVMInvokeInstruction) {
				//String mName = ((JVMInvokeInstruction) insn).getInvokedMethodName().replaceAll("\\(.*$", "");
				String mName = insn.getInvokedMethodName().replaceAll("\\(.*$", "");

				if (insn.getInvokedMethodClassName().equals(clsName)
						&& methodName.equals(mName)) {
					targetSet.add(new Pair<Integer, Integer>(tl.getGroupNum(), tl.getLineNum()));
					srcMap.put(insn.getFileLocation(), insn.getMethodInfo().getClassName());
					break;
				}
			}
		}
	}

	public Map<String, Set<String>> getClassFieldStructure() {
		return this.classFieldNameMap;
	}

	public Map<String, Set<String>> getClassMethodStructure() {
		return this.classMethodNameMap;
	}

	public int getNumberOfThreads() {
		return this.numOfThreads;
	}

	public List<String> getThreadNames() {
		List<String> a = this.threadNames;
		a = this.threadNames;
		if(this.threadNames != null)
		{
			return new ArrayList<>(this.threadNames);
		}
		return null;
		
	}

	public int getRows() {
		return path.size();
	}

	public Path getPath() {
		return this.path;
	}
	public List<Integer> getThreadIds()
	{
		return this.threadId;
	}

	public List<Pair<Integer, Integer>> getGroup() {
		return group;
	}

	public Set<Pair<Integer, Integer>> getWaitNotify() {
		return new HashSet<>(waitSet);
	}

	public Set<Pair<Integer, Integer>> getThreadStart() {
		return new HashSet<>(threadStartSet);
	}

	public Set<String> getFieldNames() {
		return new HashSet<>(fieldNames);
	}

	public Set<Pair<Integer, Integer>> getLocks(String field) {
		return new HashSet<>(lockTable.get(field));
	}

	public Map<Integer, TextLineList> getLineTable() {
		return new HashMap<>(lineTable);
	}

	public Map<Pair<Integer, Integer>, List<Pair<Integer, String>>> getThreadStateMap() {
		return new HashMap<>(threadStateMap);
	}

}
