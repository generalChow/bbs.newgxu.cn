package cn.newgxu.bbs.domain.lucky;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Transient;

import cn.newgxu.bbs.domain.Topic;
import cn.newgxu.bbs.domain.market.Item;
import cn.newgxu.bbs.web.action.LuckyTopicAction;
import cn.newgxu.jpamodel.JPAEntity;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * @path valhalla_hx----cn.newgxu.bbs.domain.lucky.Lucky.java 
 * 
 * @author 集成显卡
 * @since 4.5.0
 * @version $Revision 1.1$
 * @date 2011-10-2
 * @describe  
 * 幸运帖<br />
 * 发帖人可以定义这个幸运帖的设置，如每天/总共可以中奖多少人，中奖概率，中奖方式（答题，发帖人自己审核），题目列表，可得奖励（虚拟道具，真实礼品），到期时间。<br />
 * 
 *这种帖子的最初应用是 中加学院十周年活动 时与论坛合作所用到的推广<br />
 *同时，也可以应用到论坛上，使论坛人气更高
 *
 */
@Entity
@Table(name="topic_lucky")
public class Lucky extends JPAEntity{
	private static final long serialVersionUID=3023082478343L;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	@Column(name="count_config")
	private String countConfig;
	@Column(name="probability",precision=2)
	private float probability;//中奖概率
	@Column(name="min_count")
	private int minCount=1;//当答对多少题目时可以参与砸蛋
	@Column(name="type")
	private int type;//中奖方式
	@Column(name="count_current")
	private int count;//当前中奖人数
	@Column(name="can_multiple")
	private boolean multiple;
	@Column(name="timeout")
	private int timeout;
	@Column(name="date_start")
	private Date startDate;
	@Column(name="date_end")
	private Date endDate;
	@Column(name="date_create")
	private Date createDate;
	@Column(name="information")
	private String information;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="lucky_id")
	@OrderBy("id")
	private List<LuckySubject> subjects;
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	@JoinColumn(name="lucky_id")
	@OrderBy("id")
	private List<LuckyGift> gifts;
	@OneToOne(cascade={CascadeType.ALL},fetch=FetchType.LAZY)
	@JoinColumn(name="topic_id")
	private Topic topic;
	
	@Transient
	private List<String> giftsInfo=null;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCountConfig() {
		return countConfig;
	}
	public void setCountConfig(String countConfig) {
		this.countConfig = countConfig;
	}
	public float getProbability() {
		return probability;
	}
	public void setProbability(float probability) {
		this.probability = probability;
	}
	public int getMinCount() {
		return minCount;
	}
	public void setMinCount(int minCount) {
		this.minCount = minCount;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	public boolean isMultiple() {
		return multiple;
	}
	public void setMultiple(boolean multiple) {
		this.multiple = multiple;
	}
	public List<LuckySubject> getSubjects() {
		return subjects;
	}
	public void setSubjects(List<LuckySubject> subjects) {
		this.subjects = subjects;
	}
	public Date getStartDate() {
		return startDate;
	}
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	public Date getEndDate() {
		return endDate;
	}
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	public String getInformation() {
		return information;
	}
	public void setInformation(String information) {
		this.information = information;
	}
	public int getSubjectSize(){
		return this.subjects.size();
	}
	/**幸运砸蛋对应的帖子，他们是一对一的关系*/
	public Topic getTopic() {
		return topic;
	}
	public void setTopic(Topic topic) {
		this.topic = topic;
	}
	public List<LuckyGift> getGifts() {
		return gifts;
	}
	public void setGifts(List<LuckyGift> gifts) {
		this.gifts = gifts;
	}
	/**答题可用时间*/
	public int getTimeout() {
		return timeout;
	}
	public void setTimeout(int timeout) {
		this.timeout = timeout;
	}
	
	/**
	 * 获取当个 LuckyGift 描述性信息
	 * @param lg
	 * @return
	 */
	public static String singleGiftsInfo(LuckyGift lg){
		String result="";
		switch(lg.getType()){
		case LuckyGift.REALITY_GIFT:
			result=lg.getValue();
			break;
		case LuckyGift.FORUM_ITEM:
			try{
				int itemId=Integer.valueOf(lg.getValue());
				Item item=Item.get(itemId);
				result=(item.getName()+"----"+item.getEffect());
			}catch(Exception e){
				result=("<span style='color:#c00;'>加载论坛道具出错，可能对应编号["+lg.getValue()+"]的道具已经不存在！</span>");
			}
			break;
		case LuckyGift.FORUM_MONEY:
			result=("西大币&nbsp;"+lg.getValue());
			break;
		default:
			result=("<span style='color:#c00;'>礼品类型异常["+lg.getType()+"]！</span>");
		}
		return result;
	}
	/**
	 * 获取礼物的描述
	 * @return
	 */
	public List<String> getGiftsInfo(){
		if(giftsInfo==null){
			giftsInfo=new ArrayList<String>();
			for(LuckyGift lg:gifts){
				giftsInfo.add(singleGiftsInfo(lg));
			}
		}
		return giftsInfo;
	}
	
	public void addCount(int step){
		this.count+=step;
		count=count<0?0:count;
	}
	/**
	 * 返回的是 这个幸运帖子的 中奖人数设置说明：<br />
	 * 有每天 n 人，一共n人两种
	 * 
	 * @return
	 */
	public String getCountInfo(){
		String result="";
		String count=countConfig.substring(0, countConfig.indexOf("/"));
		if(this.countConfig.endsWith(LuckyConfig.DAY_COUNT_CONFIG)){
			result+="每天最多"+count+"人中奖";
		}else{
			result+="最大中奖人数为"+count;
		}
		return result;
	}
	
	public static Lucky get(int id) throws ObjectNotFoundException{
		return (Lucky)Lucky.getById(Lucky.class, id);
	}
	
	/**
	 * 是否可以参加这个幸运帖，只要日期没过,就可以参加<br />
	 * @param id
	 * @return
	 * @throws ObjectNotFoundException
	 */
	public static boolean canJoin(Integer id){
		try{
			boolean result=true;
			Lucky lucky=get(id);
			String temp=lucky.getCountConfig().substring(0, lucky.getCountConfig().indexOf("/"));
			int maxCount=Integer.valueOf(temp);//最大中奖人数
			if(lucky.getCountConfig().endsWith(LuckyConfig.DAY_COUNT_CONFIG)){
				int current=LuckyLog.getLuckerCount(new Date());
				System.out.println("目前中奖："+current);
				result=result&&(maxCount>current);
			}else{
				result=result&&(maxCount>lucky.getCount());
			}
			result=result&&(lucky.getEndDate().after(new Date()));
			return result;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
	}
	
	/**
	 * 随机获取题目，题目量在 count--最大题目数之间<br />
	 * 从原来的 List 中remove 一些元素即可
	 * @return
	 */
	public List<LuckySubject> getRandomSubjects(){
		if(getMinCount()>=getSubjectSize())
			return this.getSubjects();
		List<LuckySubject> result=new ArrayList<LuckySubject>();
		Random random =new Random();
		for(int i=0;i<10;i++){
			int temp=random.nextInt(getSubjectSize());
			result.add(getSubjects().remove(temp));
		}
		return result;
	}
	
	/**
	 * 获取中奖码
	 * @return
	 */
	public static String getCodeNumber(){
		String letters="ABCDEF";
		Random random=new Random();
		String result="";
		for(int i=0;i<4;i++){
			result+=random.nextInt(10);
		}
		result+=letters.charAt(random.nextInt(6));
		return result;
	}
	
	public static void main(String a[]){
		System.out.println(Lucky.getCodeNumber());
		System.out.println(Lucky.getCodeNumber());
		System.out.println(Lucky.getCodeNumber());
		String base="[url=/www.yws?s=1]去[/url]";
		System.out.println(LuckyTopicAction.urlConvert(base));
	}
}
