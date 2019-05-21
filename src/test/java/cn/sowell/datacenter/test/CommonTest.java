package cn.sowell.datacenter.test;

import com.abc.extface.dto.AggregateExpressionDTO;
import com.abc.model.AggregateExpression;
import com.abc.model.IAggregateExpressionDAO;

public class CommonTest {
	
	public void temp() {
		AggregateExpression aggregateExpression=new AggregateExpression(147,new AAggregateExpressionDAO());
		String expression = aggregateExpression.getExpression();
	}
	
	public class AAggregateExpressionDAO implements IAggregateExpressionDAO{

		@Override
		public AggregateExpressionDTO query(Integer id) {
			
			
			return new AggregateExpressionDTO();
		}
		
	}

}
