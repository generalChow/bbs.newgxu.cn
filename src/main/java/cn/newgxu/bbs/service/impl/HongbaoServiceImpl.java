package cn.newgxu.bbs.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import cn.newgxu.bbs.common.exception.BBSException;
import cn.newgxu.bbs.common.exception.BBSExceptionMessage;
import cn.newgxu.bbs.common.util.Util;
import cn.newgxu.bbs.domain.hongbao.HongBao;
import cn.newgxu.bbs.domain.hongbao.HongBao_content;
import cn.newgxu.bbs.domain.hongbao.HongBao_user;
import cn.newgxu.bbs.domain.market.Item;
import cn.newgxu.bbs.domain.market.ItemLine;
import cn.newgxu.bbs.domain.user.User;
import cn.newgxu.bbs.service.HongbaoService;
import cn.newgxu.bbs.web.model.hongbao.HongbaoManageModel;
import cn.newgxu.bbs.web.model.hongbao.HongbaoModel;
import cn.newgxu.bbs.web.model.hongbao.HongbaoRecieveModel;
import cn.newgxu.bbs.web.model.hongbao.HongbaoTypeModel;
import cn.newgxu.jpamodel.ObjectNotFoundException;

/**
 * 
 * @author hjc
 * @since 4.0.0
 * @version $Revision 1.1$
 */
public class HongbaoServiceImpl implements HongbaoService {

	private static final Log log = LogFactory.getLog(HongbaoServiceImpl.class);

	public void createHongbao(HongbaoModel model) {
		HongBao hongbao = new HongBao();
		hongbao.setName(model.getName());
		hongbao.setDescription(model.getDescription());
		hongbao.setHistory(model.getHistory());
		hongbao.setTime(model.getTime());
		hongbao.setValid(1);
		try {
			hongbao.setContent(HongBao_content.getByContentId(model
					.getContentType()));
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
		hongbao.save();
	}

	public void createHongbaoType(HongbaoTypeModel model) {
		HongBao_content content = new HongBao_content();
		content.setExp(model.getExp());
		content.setName(model.getName());
		content.setMoney(model.getMoney());
		List<String> itemNames = Util.splitNicks(model.getItems());
		List<Item> items = new ArrayList<Item>();
		Item item = null;
		for (int i = 0; i < itemNames.size(); i++) {
			try {
				item = Item.getByName(itemNames.get(i));
			} catch (ObjectNotFoundException e) {
				e.printStackTrace();
			}
			items.add(item);
		}
		content.setItems(items);
		content.save();
	}

	public void manageHongbao(HongbaoManageModel model) {
		List<HongBao> list = HongBao.getAllHongBaos();
		model.setHongbaos(list);
	}

	public void manageHongbaoType(HongbaoModel model) {
		model.setHongbaoTypes(HongBao_content.getHongBaoContents());
	}

	public void getHongbao(HongbaoModel model) {
		HongBao hongbao = null;
		try {
			hongbao = HongBao.getById(model.getId());
			model.setContentType(hongbao.getContent().getId());
			model.setName(hongbao.getName());
			model.setHistory(hongbao.getHistory());
			model.setDescription(hongbao.getDescription());
			model.setTime(hongbao.getTime());
			model.setHongbaoTypes(HongBao_content.getHongBaoContents());
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void editHongbao(HongbaoModel model) {
		try {
			HongBao hongbao = new HongBao();
			hongbao.setId(model.getId());
			hongbao.setContent(HongBao_content.getByContentId(model
					.getContentType()));
			hongbao.setDescription(model.getDescription());
			hongbao.setHistory(model.getHistory());
			hongbao.setName(model.getName());
			hongbao.setTime(model.getTime());
			hongbao.setValid(model.getValid());
			hongbao.update();
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void delHongbao(HongbaoModel model) {
		try {
			HongBao hongbao = HongBao.getById(model.getId());
			hongbao.delete();
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void delHongbaoType(HongbaoModel model) {
		try {
			HongBao_content hongbao = HongBao_content.getByContentId(model
					.getId());
			hongbao.delete();
		} catch (ObjectNotFoundException e) {
			e.printStackTrace();
		}
	}

	public void getHongbaoToday(HongbaoRecieveModel model) {
		// 确定今日有否红包，下次红包发放时间
		SimpleDateFormat sdf = new SimpleDateFormat("MMdd");
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		int today = Integer.parseInt(sdf.format(new Date()));
		String todayInfo = sdf2.format(new Date());
		try {
			System.out.println("有红包：：："+today);
			HongBao next = getNextFestival(today);
			String nextInfo = (int) (next.getTime() / 100) + "月"
					+ next.getTime() % 100 + "日";
			model.setNextFestival(nextInfo);
			HongBao hongbaoToday = HongBao.getHongBaoToday(today);
			model.setHongbao(hongbaoToday);
			model.setToday(todayInfo + " 有 " + hongbaoToday.getName());
			model.setIsFestivalToday(1);
		} catch (ObjectNotFoundException e1) {
			log.debug("没有红包");
			model.setIsFestivalToday(0);
			model.setToday(todayInfo + " 没有红包");
			return;
		}
		// 确定今日是否已经领过红包
		int isRecieveToday = 0;
		try {
			List<HongBao_user> hus = HongBao_user.getHongbaoByUser(model
					.getUser());
			System.out.println(Util.getDate(new Date()));
			System.out.println(Util.getDate(Util.getDateAfterDay(1)));
			for (int i = 0; i < hus.size(); i++) {
				Date lasttime = hus.get(i).getLasttime();
				if (lasttime.after(Util.getDate(new Date()))) {
					isRecieveToday = 1;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			isRecieveToday = 0;// 抛异常说明没有领取礼物的记录
		} finally {
			model.setIsRecieveToday(isRecieveToday);
		}
	}

	private HongBao getNextFestival(int today) {
		return HongBao.getNextFestival(today);
	}

	public void recieveHongbaoToday(HongbaoRecieveModel model)
			throws BBSException {
		getHongbaoToday(model);
		if (model.getUser().getExp() < 200) {
			throw new BBSException(BBSExceptionMessage.NEED_EXP_HONGBAO);
		}
		// 如果今天不是节日或者已经领过礼物，则抛出异常
		if (model.getIsFestivalToday() == 1 && model.getIsRecieveToday() != 1) {
			User user = model.getUser();
			HongBao hongbao = model.getHongbao();
			HongBao_content hc = hongbao.getContent();
			user.addExp(hc.getExp());
			user.addMoney(hc.getMoney());
			user.save();
			List<Item> itemList = hongbao.getContent().getItems();
			for (int i = 0; i < itemList.size(); i++) {
				ItemLine itemLine = new ItemLine();
				itemLine.setUser(user);
				itemLine.setItem(itemList.get(i));
				itemLine.save();
			}
			HongBao_user hu = new HongBao_user();
			hu.setUser(user);
			hu.setHongbao(model.getHongbao());
			hu.setLasttime(new Date());
			hu.save();
		} else {
			throw new BBSException(BBSExceptionMessage.NO_HONGBAO);
		}
	}

}
