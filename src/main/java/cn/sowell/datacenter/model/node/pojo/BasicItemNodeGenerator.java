package cn.sowell.datacenter.model.node.pojo;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * BasicItem 实体和属性规则生成
 * @author so-well
 *
 */
@Entity
@Table(name = "t_sc_basic_item_code_generator")
public class BasicItemNodeGenerator {
	private static Properties props;
	
	static {
		loadProps();
	}
	
	
	 synchronized static private void loadProps(){
	        props = new Properties();
	        InputStream in = null;
	        try {
	        	in = BasicItemNodeGenerator.class.getClassLoader().getResourceAsStream("model.properties");
				props.load(in);
	        } catch (Exception e) {
	            e.printStackTrace();
	        }finally {
	            try {
	                if(null != in) {
	                    in.close();
	                }
	            } catch (IOException e) {
	                e.printStackTrace();
	            }
	        }
	    }

	    public static String getProperty(String key){
	        if(null == props) {
	            loadProps();
	        }
	        return props.getProperty(key);
	    }

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private Integer id;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

}