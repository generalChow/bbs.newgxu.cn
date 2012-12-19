package cn.newgxu.bbs.domain.lucky;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * @path valhalla_hx----cn.newgxu.bbs.domain.lucky.LuckyOption.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-2
 * @describe  
 *	幸运帖的问题选项，当幸运帖的活动方式为 答题时，这个是有效的
 */
@Entity
@Table(name="topic_lucky_option")
public class LuckySubject extends JPAEntity{
	private static final long serialVersionUID=812397138232L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="title")
	private String title;//题目内容
	@Column(name="answer")
	private String answer;
	@Column(name="option_string")
	private String optionString;//选项所组成的字符串
	
	/**选项列表，由optionString解析得到*/
	@Transient
	private List<String> optionList;
	
	@ManyToOne(cascade={CascadeType.REFRESH},fetch=FetchType.LAZY)
	@JoinColumn(name="lucky_id")
	private Lucky lucky;
	
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Lucky getLucky() {
		return lucky;
	}
	public void setLucky(Lucky lucky) {
		this.lucky = lucky;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getAnswer() {
		return answer;
	}
	public void setAnswer(String answer) {
		this.answer = answer;
	}
	public String getOptionString() {
		return optionString;
	}
	public void setOptionString(String optionString) {
		this.optionString = optionString;
	}
	
	public List<String> getOptionList() {
		if(optionList==null){
			optionList=new ArrayList<String>();
			String temp[]=this.optionString.split(LuckyConfig.SPLIT_CHAR);
			for (String string : temp) {
				optionList.add(string);
			}
		}
		return optionList;
	}
	public void setOptionList(List<String> optionList) {
		this.optionList = optionList;
	}
	
	public static LuckySubject get(int id) throws ObjectNotFoundException{
		return (LuckySubject)LuckySubject.getById(LuckySubject.class, id);
	}
	
	/**
	 * 传入一个数组，将其转换成String
	 * @param list
	 */
	public void setOptionList(String[] list){
		if(list.length==0)
			return ;
		optionString=list[0];
		for(int i=1;i<list.length;i++)
			optionString+=LuckyConfig.SPLIT_CHAR+list[i];
	}
}
