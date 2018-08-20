package cn.sowell.datacenter.utils;

import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;

import com.abc.mapping.node.NodeOpsType;
import com.abc.mapping.node.NodeType;

public class NodeTypeMappingOps {
	private static Map<NodeType, Collection<NodeOpsType>> opsMapping = new HashMap();

	public static Collection<NodeOpsType> getOpsSetByNodeType(NodeType nodeType) {
		return (Collection) opsMapping.get(nodeType);
	}

	static {
		HashSet opsTypes = new HashSet();
		
		opsMapping.put(NodeType.ABC, opsTypes);
		opsTypes.add(NodeOpsType.READ);
		opsTypes.add(NodeOpsType.WRITE);
		opsTypes = new HashSet();
		
		opsMapping.put(NodeType.ATTRIBUTE, opsTypes);
		opsTypes.add(NodeOpsType.READ);
		opsTypes.add(NodeOpsType.WRITE);
		opsTypes.add(NodeOpsType.SUPPLEMENT);
		opsTypes = new HashSet();
		opsMapping.put(NodeType.LABEL, opsTypes);
		for (NodeOpsType opsType : NodeOpsType.values()) {
			opsTypes.add(opsType);
		}
		
		opsTypes = new HashSet();
		opsMapping.put(NodeType.MULTIATTRIBUTE, opsTypes);
		for (NodeOpsType opsType : NodeOpsType.values()) {
			opsTypes.add(opsType);
		}
		
		opsTypes = new HashSet();
		opsMapping.put(NodeType.RELATION, opsTypes);
		for (NodeOpsType opsType : NodeOpsType.values()) {
			opsTypes.add(opsType);
		}
		
		opsTypes = new HashSet();
		opsMapping.put(NodeType.ATTRGROUP, opsTypes);
		opsTypes.add(NodeOpsType.READ);
		opsTypes.add(NodeOpsType.WRITE);
		opsTypes.add(NodeOpsType.SUPPLEMENT);
		
		opsTypes = new HashSet();
		opsMapping.put(NodeType.CASATTRIBUTE, opsTypes);
		opsTypes.add(NodeOpsType.READ);
		opsTypes.add(NodeOpsType.WRITE);
		opsTypes.add(NodeOpsType.SUPPLEMENT);
	}
	
	//返回true：可以修改， false：不可以修改
	public static boolean compareOpt(Collection optColl, NodeOpsType curOpt) {
		Iterator iterator = optColl.iterator();
		while (iterator.hasNext()) {
			String next = (String) iterator.next();
			NodeOpsType opsType = NodeOpsType.getNodeOpsType(next);
			boolean include = curOpt.include(opsType);
			if (!include) {
				return false;
			}
		}
		
		return true;
	}
	
	
}
