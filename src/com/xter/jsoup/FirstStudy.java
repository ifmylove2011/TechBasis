package com.xter.jsoup;

//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;
//
//import java.io.IOException;

/**
 * @author XTER
 * 项目名称: TechBasis
 * 创建时间: 2020/9/21
 * 描述:
 */
public class FirstStudy {

	public static void main(String[] args) {
		demo();
		System.out.println("----------------");
		parse();
	}

	public static void fetchUrl(String url){

	}

	public static void demo(){
//			Document doc = Jsoup.parse(SY30_1);
//			System.out.println(doc.select(".librarylist"));
//			System.out.println(doc.getElementsByClass("info").first().getElementsByTag("span").get(0).getElementsByTag("a").text());
//			System.out.println(doc.getElementsByClass("info").first().getElementsByTag("span").get(1).getElementsByTag("a").text());
//			System.out.println(doc.getElementsByClass("info").first().getElementsByTag("span").get(2).getElementsByTag("a").text());
//		System.out.println(doc.getElementsByClass("pt-ll-l").first().getElementsByTag("a"));
	}

	public static void parse(){
//		try {
//			Document doc = Jsoup.connect("https://www.30sy.com/search.html")
//					.data("searchtype","novelname")
//					.data("searchkey","都市之创造万界")
//					.userAgent("Mozilla/5.0 (Windows NT 6.1; rv:30.0) Gecko/20100101 Firefox/30.0")
//					.get();
////			System.out.println(doc.toString());
////			String title = doc.title();
//			System.out.println(doc.title());
//		} catch (IOException e) {
//			e.printStackTrace();
////		}
//		Document doc = Jsoup.parse(BIWU_1);
//		System.out.println(doc.getElementById("nr").getElementsByTag("dt").get(0).getElementsByTag("a").get(0).getElementsByTag("img").attr("_src"));
//		System.out.println(doc.getElementById("nr").getElementsByClass("book_des").get(0).text());
	}
	private static final String SY30_1 = "<!doctype html>\n" +
			"<html lang=\"zh-cmn-Hans\"> \n" +
			" <head> \n" +
			"  <meta charset=\"utf-8\"> \n" +
			"  <title>搜索“都市之创造万界”的结果</title> \n" +
			"  <meta name=\"keywords\" content=\"都市之创造万界,30书院\"> \n" +
			"  <meta name=\"description\" content=\"30书院专注于玄幻小说搜索,新版快眼看书,提供最全的小说保持最快的更新,方便大家愉快地阅读玄幻小说\"> \n" +
			"  <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\"> \n" +
			"  <meta name=\"renderer\" content=\"webkit\"> \n" +
			"  <meta name=\"viewport\" content=\"width=device-width\"> \n" +
			"  <meta http-equiv=\"Cache-Control\" content=\"no-siteapp\"> \n" +
			"  <meta http-equiv=\"Cache-Control\" content=\"no-transform \"> \n" +
			"  <link rel=\"shortcut icon\" type=\"image/ico\" href=\"/favicon.ico\"> \n" +
			"  <meta name=\"applicable-device\" content=\"pc\"> \n" +
			"  <meta http-equiv=\"mobile-agent\" content=\"format=html5; url=https://m.30sy.com/search.html?searchtype=novelname&amp;searchkey=%E9%83%BD%E5%B8%82%E4%B9%8B%E5%88%9B%E9%80%A0%E4%B8%87%E7%95%8C\"> \n" +
			"  <link rel=\"stylesheet\" href=\"https://www.30sy.com/template/default/public/css/global.css\"> \n" +
			"  <link rel=\"stylesheet\" href=\"https://www.30sy.com/template/default/public/css/style.css\"> \n" +
			"  <script type=\"text/javascript\" src=\"/public/ptcms/m.js\"></script> \n" +
			" </head> \n" +
			" <body> \n" +
			"  <header> \n" +
			"   <div class=\"ann\"> \n" +
			"   </div> \n" +
			"   <div class=\"banner \"> \n" +
			"    <div class=\"w-all\"> \n" +
			"     <ul class=\"fl\"> \n" +
			"      <li><a href=\"/user/public/login.html\">登陆账号</a></li> \n" +
			"      <li><a href=\"/user/public/register.html\">免费注册</a></li> \n" +
			"     </ul> \n" +
			"     <ul class=\"fr\"> \n" +
			"      <li><i class=\"pticon pticon-mobile pticon-lg\"></i><a href=\"https://m.30sy.com\" target=\"_blank\" title=\"30书院手机版\">手机版</a></li> \n" +
			"      <li><i class=\"pticon pticon-home pticon-lg\"></i><a href=\"javascript:$.pt.setHome('https://www.30sy.com/');\">设为首页</a></li> \n" +
			"      <li><i class=\"pticon pticon-star pticon-lg\"></i><a href=\"javascript:$.pt.addFav();\">加入收藏</a></li> \n" +
			"      <li><i class=\"pticon pticon-desktop\"></i><a href=\"/novelsearch/index/desktop.html\" target=\"_blank\">桌面快捷</a></li> \n" +
			"     </ul> \n" +
			"    </div> \n" +
			"   </div> \n" +
			"   <div class=\"clear\"></div> \n" +
			"   <div class=\"logosearch w-all \"> \n" +
			"    <div class=\"logo fl\">\n" +
			"     <a href=\"https://www.30sy.com/\" title=\"30书院\"><img src=\"/template/default/public/image/logo.png\" alt=\"30书院\"></a>\n" +
			"    </div> \n" +
			"    <form class=\"searchbox fl\" action=\"/search.html\"> \n" +
			"     <div class=\"dropmenu\"> <span class=\"tri\"></span> \n" +
			"      <ul class=\"dropmenu-item\"> \n" +
			"       <li data-type=\"novelname\">书名</li> \n" +
			"       <li data-type=\"author\">作者</li> \n" +
			"      </ul> \n" +
			"     </div> \n" +
			"     <input name=\"searchtype\" type=\"hidden\" value=\"novelname\"> <label class=\"fl\"><input type=\"text\" name=\"searchkey\" id=\"\" class=\"text\"></label> \n" +
			"     <input type=\"submit\" value=\"搜索\" class=\"searchbtn\"> \n" +
			"    </form> \n" +
			"    <div class=\"operate fl\"> <a class=\"operate-item\" title=\"看过的书\" href=\"/user/history/index.html\"> <i class=\"pticon pticon-history\"></i> <b>看过的书</b> </a> <a class=\"operate-item\" title=\"QQ登陆\" href=\"/user/public/thirdlogin.html?type=qq\"> <i class=\"pticon pticon-qq\"></i> <b>QQ登陆</b> </a> <a class=\"operate-item\" title=\"微博登陆\" href=\"/user/public/thirdlogin.html?type=weibo\"> <i class=\"pticon pticon-weibo\"></i> <b>微博登陆</b> </a> <a class=\"operate-item\" title=\"用户登陆\" href=\"/user/public/login.html\"> <i class=\"pticon pticon-user\"></i> <b>用户登陆</b> </a> \n" +
			"    </div> \n" +
			"   </div> \n" +
			"   <nav class=\"w-all\"> \n" +
			"    <ul class=\"clearfix\"> \n" +
			"     <li><a href=\"https://www.30sy.com/\" title=\"30书院首页\">首页</a></li> \n" +
			"     <li><a href=\"/sort.html\" title=\"小说分类\">小说分类</a></li> \n" +
			"     <li><a href=\"/top/monthvisit.html\" title=\"点击排行\">点击排行</a></li> \n" +
			"     <li><a href=\"/top/lastupdate.html\" title=\"最近更新\">最近更新</a></li> \n" +
			"     <li><a href=\"/over/\" title=\"全本小说\">全本小说</a></li> \n" +
			"    </ul> \n" +
			"   </nav> \n" +
			"  </header> \n" +
			"  <section class=\"w-all\"> \n" +
			"   <nav class=\"mt20\"> \n" +
			"    <ul class=\"clearfix\"> \n" +
			"     <li><a href=\"https://www.30sy.com\" title=\"30书院首页\"><i class=\"pticon pticon-home pticon-lg\"></i> 30书院首页</a><i class=\"pticon pticon-angle-right icon\"></i></li> \n" +
			"     <li><a href=\"/search.html?searchtype=novelname&amp;searchkey=%E9%83%BD%E5%B8%82%E4%B9%8B%E5%88%9B%E9%80%A0%E4%B8%87%E7%95%8C\" title=\"搜索 “都市之创造万界” 结果\">搜索 “都市之创造万界” 结果</a></li> \n" +
			"    </ul> \n" +
			"   </nav> \n" +
			"   <div class=\"container clearfix mt20\"> \n" +
			"    <div class=\"w-left\"> \n" +
			"     <div class=\"card\"> \n" +
			"      <div class=\"header line\"> \n" +
			"       <h2>搜索 “<strong class=\"num\">都市之创造万界</strong>” 结果</h2> \n" +
			"      </div> \n" +
			"      <div class=\"body\"> \n" +
			"       <ul class=\"librarylist\"> \n" +
			"        <li> \n" +
			"         <div class=\"pt-ll-l\"> <a href=\"/book/dushizhichuangzaowanjie/\" title=\"都市之创造万界\"><img src=\"/public/cover/92/09/43/9209433723499742b3fdd36ca3f62679.jpg\" alt=\"都市之创造万界\"></a> \n" +
			"         </div> \n" +
			"         <div class=\"pt-ll-r\"> \n" +
			"          <p class=\"info\"> <span>《<a href=\"/book/dushizhichuangzaowanjie/\" class=\"novelname\" title=\"都市之创造万界\">都市之创造万界</a>》</span> <span>作者：<a href=\"/author/sanshengyanhuo\" title=\"三生烟火 作品列表\">三生烟火</a></span> <span>分类：<a href=\"/sort/xuanhuan.html\" title=\"玄幻小说\">玄幻</a></span> </p> \n" +
			"          <p class=\"intro\"> 得到一款脑洞越大，系统越强的系统，赵阳表示自己完全没有问题。当红花旦拍戏不慎掉落悬崖，得到奇遇，十日之后，踏空而行。此后全球灵气复苏，科技世界走上修炼时代？一个世界太小，那就创造诸天万界，所有人认知.. </p> \n" +
			"          <p class=\"last\"> 最新章节：<a href=\"/book/dushizhichuangzaowanjie/1062.html\" title=\"都市之创造万界 第一千六十二章：黑暗大帝的野望（第一更）\" target=\"_blank\">第一千六十二章：黑暗大帝的野望（第一更）</a>(09-21) </p> \n" +
			"         </div> \n" +
			"         <div class=\"clear\"></div> </li> \n" +
			"       </ul> \n" +
			"      </div> \n" +
			"     </div> \n" +
			"    </div> \n" +
			"    <div class=\"w-right\"> \n" +
			"     <div class=\"card pt-tab\"> \n" +
			"      <div class=\"header tab pt-tab-nav\"> \n" +
			"       <ul class=\"clearfix\"> \n" +
			"        <li class=\"active\">本月热门</li> \n" +
			"        <li>本周热门</li> \n" +
			"       </ul> \n" +
			"      </div> \n" +
			"      <div class=\"body pt-tab-con\"> \n" +
			"       <ul class=\"rightlist\"> \n" +
			"        <li><i class=\"first\">1</i><span><a href=\"/author/zhaoshijue\" title=\"赵史觉 作品大全\">赵史觉</a></span><a href=\"/book/pianzhinanzhubaiyueguangwobudangliao/\" title=\"偏执男主白月光我不当了\">偏执男主白月光我不当了</a></li> \n" +
			"        <li><i class=\"first\">2</i><span><a href=\"/author/chuanbao\" title=\"船宝 作品大全\">船宝</a></span><a href=\"/book/baiyueguangtishenbuganliao/\" title=\"白月光替身不干了\">白月光替身不干了</a></li> \n" +
			"        <li><i class=\"first\">3</i><span><a href=\"/author/fadadeleixian\" title=\"发达的泪腺 作品大全\">发达的泪腺</a></span><a href=\"/book/changandiyimeiren/\" title=\"长安第一美人\">长安第一美人</a></li> \n" +
			"        <li><i>4</i><span><a href=\"/author/luoqingmei\" title=\"罗青梅 作品大全\">罗青梅</a></span><a href=\"/book/jiageiyigeheshang/\" title=\"嫁给一个和尚（月明千里）\">嫁给一个和尚（月明千里）</a></li> \n" +
			"        <li><i>5</i><span><a href=\"/author/muguahuang\" title=\"木瓜黄 作品大全\">木瓜黄</a></span><a href=\"/book/zhetichaogangliao/\" title=\"这题超纲了\">这题超纲了</a></li> \n" +
			"        <li><i>6</i><span><a href=\"/author/zhenshangtingsheng\" title=\"枕上听笙 作品大全\">枕上听笙</a></span><a href=\"/book/musiyoudiantian0yulequan0/\" title=\"慕斯有点甜[娱乐圈]\">慕斯有点甜[娱乐圈]</a></li> \n" +
			"        <li><i>7</i><span><a href=\"/author/youfeiya\" title=\"幽非芽 作品大全\">幽非芽</a></span><a href=\"/book/chongshengbalingjinxiujunhun/\" title=\"重生八零锦绣军婚\">重生八零锦绣军婚</a></li> \n" +
			"        <li><i>8</i><span><a href=\"/author/tongchidaoren\" title=\"通吃道人 作品大全\">通吃道人</a></span><a href=\"/book/shidichongsheng/\" title=\"噬帝重生\">噬帝重生</a></li> \n" +
			"        <li><i>9</i><span><a href=\"/author/mingyu\" title=\"明宇 作品大全\">明宇</a></span><a href=\"/book/daizhuonongchanghunyijie/\" title=\"带着农场混异界\">带着农场混异界</a></li> \n" +
			"        <li><i>10</i><span><a href=\"/author/mingyuemanzhi\" title=\"明月满枝 作品大全\">明月满枝</a></span><a href=\"/book/bingruodiwangdeweiyangjihua/\" title=\"病弱帝王的喂养计划\">病弱帝王的喂养计划</a></li> \n" +
			"        <li><i>11</i><span><a href=\"/author/aoyedabai\" title=\"熬夜大白 作品大全\">熬夜大白</a></span><a href=\"/book/congjintiankaishidangchengzhu/\" title=\"从今天开始当城主\">从今天开始当城主</a></li> \n" +
			"        <li><i>12</i><span><a href=\"/author/lianzhuxiaoyao\" title=\"恋竹小妖 作品大全\">恋竹小妖</a></span><a href=\"/book/qingchuanjinlishisifujin/\" title=\"清穿锦鲤十四福晋\">清穿锦鲤十四福晋</a></li> \n" +
			"        <li><i>13</i><span><a href=\"/author/hongshaolongxia\" title=\"红烧龙虾 作品大全\">红烧龙虾</a></span><a href=\"/book/wodejuesemingxinglaopo/\" title=\"我的绝色明星老婆\">我的绝色明星老婆</a></li> \n" +
			"        <li><i>14</i><span><a href=\"/author/xiangweixingzhe\" title=\"相位行者 作品大全\">相位行者</a></span><a href=\"/book/jinhuadesishiliuyichongzou/\" title=\"进化的四十六亿重奏\">进化的四十六亿重奏</a></li> \n" +
			"        <li><i>15</i><span><a href=\"/author/shazhudao\" title=\"杀猪刀 作品大全\">杀猪刀</a></span><a href=\"/book/chaojiquannengxuesheng/\" title=\"超级全能学生\">超级全能学生</a></li> \n" +
			"       </ul> \n" +
			"       <ul class=\"rightlist none\"> \n" +
			"        <li><i class=\"first\">1</i><span><a href=\"/author/hualuoweiyao\" title=\"花落唯窈 作品大全\">花落唯窈</a></span><a href=\"/book/tianmaishenzhu/\" title=\"天脉神主\">天脉神主</a></li> \n" +
			"        <li><i class=\"first\">2</i><span><a href=\"/author/qingyouzhulin\" title=\"清幽竹林 作品大全\">清幽竹林</a></span><a href=\"/book/nvpeixiuxianjizhiyiludengxian/\" title=\"女配修仙记之一路登仙\">女配修仙记之一路登仙</a></li> \n" +
			"        <li><i class=\"first\">3</i><span><a href=\"/author/fadadeleixian\" title=\"发达的泪腺 作品大全\">发达的泪腺</a></span><a href=\"/book/changandiyimeiren/\" title=\"长安第一美人\">长安第一美人</a></li> \n" +
			"        <li><i>4</i><span><a href=\"/author/zhengyan\" title=\"正言 作品大全\">正言</a></span><a href=\"/book/dushiqianghuaxitongquanlingyuzhiba/\" title=\"都市强化系统全领域制霸\">都市强化系统全领域制霸</a></li> \n" +
			"        <li><i>5</i><span><a href=\"/author/yeqimei\" title=\"夜七魅 作品大全\">夜七魅</a></span><a href=\"/book/wudicongchengmokaishi/\" title=\"无敌从成魔开始\">无敌从成魔开始</a></li> \n" +
			"        <li><i>6</i><span><a href=\"/author/yishunangua\" title=\"一树南瓜 作品大全\">一树南瓜</a></span><a href=\"/book/tianqibienao0womenbulihun/\" title=\"甜妻别闹，我们不离婚\">甜妻别闹，我们不离婚</a></li> \n" +
			"        <li><i>7</i><span><a href=\"/author/zhaoshijue\" title=\"赵史觉 作品大全\">赵史觉</a></span><a href=\"/book/pianzhinanzhubaiyueguangwobudangliao/\" title=\"偏执男主白月光我不当了\">偏执男主白月光我不当了</a></li> \n" +
			"        <li><i>8</i><span><a href=\"/author/sanshiwu\" title=\"叁拾伍 作品大全\">叁拾伍</a></span><a href=\"/book/minguozhiyuandongjushang/\" title=\"民国之远东巨商\">民国之远东巨商</a></li> \n" +
			"        <li><i>9</i><span><a href=\"/author/luoqingmei\" title=\"罗青梅 作品大全\">罗青梅</a></span><a href=\"/book/jiageiyigeheshang/\" title=\"嫁给一个和尚（月明千里）\">嫁给一个和尚（月明千里）</a></li> \n" +
			"        <li><i>10</i><span><a href=\"/author/wuguanqian\" title=\"五贯钱 作品大全\">五贯钱</a></span><a href=\"/book/woshiwangfuming/\" title=\"我是旺夫命\">我是旺夫命</a></li> \n" +
			"        <li><i>11</i><span><a href=\"/author/adiaobudiaoya\" title=\"阿刁不刁呀 作品大全\">阿刁不刁呀</a></span><a href=\"/book/kuaichuanzhigenshenxiantanlianai/\" title=\"快穿之跟神仙谈恋爱\">快穿之跟神仙谈恋爱</a></li> \n" +
			"        <li><i>12</i><span><a href=\"/author/weiyi51\" title=\"玮怡 作品大全\">玮怡</a></span><a href=\"/book/jipinxiannong8/\" title=\"极品仙农\">极品仙农</a></li> \n" +
			"        <li><i>13</i><span><a href=\"/author/bingtian5\" title=\"冰甜 作品大全\">冰甜</a></span><a href=\"/book/huoyingzhiwozaimuyezajindan/\" title=\"火影之我在木叶砸金蛋\">火影之我在木叶砸金蛋</a></li> \n" +
			"        <li><i>14</i><span><a href=\"/author/huifeidezhu\" title=\"会飞的猪 作品大全\">会飞的猪</a></span><a href=\"/book/lihailiaowodedamowang/\" title=\"厉害了我的大魔王\">厉害了我的大魔王</a></li> \n" +
			"        <li><i>15</i><span><a href=\"/author/paojiaoweiliulian\" title=\"泡椒味榴莲 作品大全\">泡椒味榴莲</a></span><a href=\"/book/congxiaoguaidaoBOSS/\" title=\"从小怪到BOSS\">从小怪到BOSS</a></li> \n" +
			"       </ul> \n" +
			"      </div> \n" +
			"     </div> \n" +
			"    </div> \n" +
			"   </div> \n" +
			"  </section> \n" +
			"  <footer> \n" +
			"   <div class=\"copyright w-all tac\"> <a href=\"/about/about.html\" title=\"关于我们\">关于我们</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href=\"/about/disdaimer.html\" title=\"免责声明\">免责声明</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href=\"/about/privacy.html\" title=\"隐私条款\">隐私条款</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href=\"/about/spider.html\" title=\"蜘蛛协议\">蜘蛛协议</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href=\"/about/zjhl.html\" title=\"章节混乱解决办法\">章节混乱解决办法</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href=\"/about/employ.html\" title=\"申请收录\">申请收录</a>&nbsp;&nbsp;&nbsp;&nbsp; <a href=\"/about/contact.html\" title=\"联系我们\">联系我们</a>&nbsp;&nbsp;&nbsp;&nbsp; \n" +
			"    <br> Copyright © 2009-2014 <a href=\"http://www.30sy.com\" target=\"_blank\">30书院</a> All Rights Reserved .版权所有30书院。\n" +
			"    <br> 本站内容系蜘蛛自动根据您的指令搜索各大小说站得到的链接列表，本站所展示内容均为实时从其他网站转码得来（类似百度转码），不存储任何实际内容。\n" +
			"    <br> 如果版权人认为在本站放置您的作品有损您的利益，请联系源网站删除内容，本站即无法对其进行转码展示，也可联系我们删除本站内容。 \n" +
			"   </div> \n" +
			"  </footer> \n" +
			"  <div class=\"gotop\">\n" +
			"   <i class=\"pticon pticon-chevron-up\"></i>\n" +
			"  </div> \n" +
			"  <script type=\"text/javascript\" src=\"https://lib.sinaapp.com/js/jquery/1.8.3/jquery.min.js\"></script> \n" +
			"  <script type=\"text/javascript\" src=\"https://www.30sy.com/template/default/public/script/common.js\"></script> \n" +
			"  <div style=\"display:none\"> \n" +
			"   <script type=\"text/javascript\" src=\"/public/ptcms/tongji.js\"></script>\n" +
			"  </div>  \n" +
			" </body>\n" +
			"</html>";

	private static final String BIWU_1 = "<!DOCTYPE html>\n" +
			"<!-- saved from url=(0031)https://www.biwu.cc/search.html -->\n" +
			"<html><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
			"\n" +
			"<title>搜索“都市之创造万界”的结果</title>\n" +
			"<meta name=\"keywords\" content=\"都市之创造万界,笔屋小说网\">\n" +
			" <meta name=\"description\" content=\"笔屋小说网专注于玄幻小说搜索,新版快眼看书,提供最全的小说保持最快的更新,方便大家愉快地阅读玄幻小说\">\n" +
			"<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no\">\n" +
			"<link rel=\"apple-touch-icon\" href=\"https://www.biwu.cc/apple-touch-icon-114x114.png\">\n" +
			"<link rel=\"apple-touch-icon-precomposed\" href=\"https://www.biwu.cc/apple-touch-icon-114x114.png\">\n" +
			"<meta name=\"applicable-device\" content=\"pc,mobile\">\n" +
			"<meta http-equiv=\"Cache-Control\" content=\"no-transform\">\n" +
			"<meta http-equiv=\"Cache-Control\" content=\"no-siteapp\">\n" +
			"<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge,chrome=1\">\n" +
			"<meta name=\"apple-mobile-web-app-title\" content=\"笔屋小说\">\n" +
			"<meta name=\"application-name\" content=\"笔屋小说\">\n" +
			"<meta name=\"apple-mobile-web-app-capable\" content=\"yes\">\n" +
			"<meta name=\"apple-mobile-web-app-status-bar-style\" content=\"default\">\n" +
			"<meta name=\"renderer\" content=\"webkit\">\n" +
			"<link rel=\"stylesheet\" type=\"text/css\" href=\"./搜索“都市之创造万界”的结果_biwu_files/style.css\" media=\"all\">\n" +
			"<script src=\"./搜索“都市之创造万界”的结果_biwu_files/push.js\"></script><script type=\"text/javascript\" src=\"./搜索“都市之创造万界”的结果_biwu_files/jquery.min.js\"></script>\n" +
			"<script type=\"text/javascript\" src=\"./搜索“都市之创造万界”的结果_biwu_files/biwu.js\"></script>\n" +
			"<script language=\"javascript\" type=\"text/javascript\" src=\"./搜索“都市之创造万界”的结果_biwu_files/biwu(1).js\"></script>\n" +
			"\n" +
			"<style type=\"text/css\" abt=\"234\"></style><script>//remove baidu search ad\n" +
			"var _countAA = 0\n" +
			"function doBBBd(){\n" +
			"    var alla = document.getElementsByTagName('a')\n" +
			"    for(var i = 0; i < alla.length; i++){\n" +
			"        if(/baidu.com\\/(baidu.php\\?url=|adrc.php\\?t)/.test(alla[i].href)){\n" +
			"            var _temp = alla[i].parentElement, loop = 0\n" +
			"            while(loop < 5){\n" +
			"                _temp = _temp.parentElement\n" +
			"                loop++\n" +
			"                if(_temp.parentElement.id == 'content_left'){\n" +
			"                    _temp.remove()\n" +
			"                    break\n" +
			"                }\n" +
			"            }\n" +
			"        }\n" +
			"    }\n" +
			"    \n" +
			"    if(_countAA++ < 20){\n" +
			"        setTimeout(doBBBd, 500)\n" +
			"    }\n" +
			"    \n" +
			"}\n" +
			"doBBBd()\n" +
			"document.addEventListener('keyup', function(){_countAA-=10;doBBBd()}, false)\n" +
			"document.addEventListener('click', function(){_countAA-=10;doBBBd()}, false)\n" +
			"//remove sohu video ad\n" +
			"//if (document.URL.indexOf(\"tv.sohu.com\") >= 0){\n" +
			"//    if (document.cookie.indexOf(\"fee_status=true\")==-1){document.cookie='fee_status=true'};\n" +
			"//}\n" +
			"//remove 56.com video ad\n" +
			"//if (document.URL.indexOf(\"56.com\") >= 0){\n" +
			"//    if (document.cookie.indexOf(\"fee_status=true\")==-1){document.cookie='fee_status=true'};\n" +
			"//}\n" +
			"</script></head>\n" +
			"<body>\n" +
			"<div class=\"top\">\n" +
			"    <div class=\"bar\">\n" +
			"          <span class=\"loginSide\"><a href=\"javascript:T.addBookMark();\">收藏本站（ Ctrl+D ）</a></span>\n" +
			"          <ul>\n" +
			"         <script>login();</script><form name=\"frmlogin\" method=\"post\" target=\"_top\" action=\"https://www.biwu.cc/user/public/login.html\"><input type=\"text\" name=\"username\" placeholder=\"账户\" class=\"putk\">&nbsp;&nbsp;<input type=\"password\" name=\"password\" placeholder=\"密码\" class=\"putk\"><input type=\"submit\" name=\"submit\" class=\"logint\" value=\"登录\">&nbsp;&nbsp;<a href=\"https://www.biwu.cc/user/public/register.html\">注册</a><input type=\"hidden\" name=\"action\" value=\"login\"></form>\n" +
			"\n" +
			"    </ul>\n" +
			"      </div>\n" +
			"  </div>\n" +
			"  <div id=\"header\">\n" +
			"      <div class=\"wrap980\">\n" +
			"          <div class=\"logo\"><a href=\"https://www.biwu.cc/\"><h1>笔屋小说网</h1></a></div>\n" +
			"          <div class=\"search\">\n" +
			"              <form id=\"search\" name=\"t_frmsearch\" method=\"post\" action=\"https://www.biwu.cc/search.html\">\n" +
			"              \t<span class=\"searchBox\"><input name=\"searchkey\" type=\"text\" onblur=\"if(this.value==&#39;&#39;){this.value=&#39;书名｜可少字但别错字，如：重生&#39;;}\" onfocus=\"if(this.value==this.defaultValue){this.value=&#39;&#39;;}\" value=\"可少字但别错字，如：重生\">\n" +
			"              \t</span>\n" +
			"              \t<button type=\"submit\" class=\"serBtn\">搜索</button></form>\n" +
			"      <div class=\"upload\"><a href=\"https://www.biwu.cc/user/history/index.html\" target=\"_blank\">阅读记录</a></div>\n" +
			"          <div class=\"hot\">热搜：\n" +
			"                                      <a href=\"https://www.biwu.cc/949243.html\">斗罗之造梗抽奖系统</a>\n" +
			"                     <a href=\"https://www.biwu.cc/856530.html\">都市:开局快递奖励10个亿！</a>\n" +
			"                     <a href=\"https://www.biwu.cc/644261.html\">火影：我有无限技能点</a>\n" +
			"                     <a href=\"https://www.biwu.cc/876444.html\">神豪从签到十亿开始</a>\n" +
			"                     <a href=\"https://www.biwu.cc/404.html\">都市之国术无双</a>\n" +
			"                     </div>\n" +
			"          </div>\n" +
			"          <div class=\"clearfix\"></div>\n" +
			"      </div>\n" +
			"      <div class=\"nav\">\n" +
			"          <ul>\n" +
			"            <li><a id=\"shouye\" href=\"https://www.biwu.cc/\">首页</a></li>\n" +
			"                                    <li><a id=\"sort1\" href=\"https://www.biwu.cc/l/xuanhuanqihuan.html\" title=\"玄幻奇幻小说\">玄幻奇幻</a></li>\n" +
			"                        <li><a id=\"sort2\" href=\"https://www.biwu.cc/l/wuxiaxianxia.html\" title=\"武侠仙侠小说\">武侠仙侠</a></li>\n" +
			"                        <li><a id=\"sort3\" href=\"https://www.biwu.cc/l/dushiyanqing.html\" title=\"都市言情小说\">都市言情</a></li>\n" +
			"                        <li><a id=\"sort4\" href=\"https://www.biwu.cc/l/lishijunshi.html\" title=\"历史军事小说\">历史军事</a></li>\n" +
			"                        <li><a id=\"sort5\" href=\"https://www.biwu.cc/l/youxijingji.html\" title=\"游戏竞技小说\">游戏竞技</a></li>\n" +
			"                        <li><a id=\"sort6\" href=\"https://www.biwu.cc/l/kehuanlingyi.html\" title=\"科幻灵异小说\">科幻灵异</a></li>\n" +
			"                        <li><a id=\"sort7\" href=\"https://www.biwu.cc/l/nvpinxiaoshuo.html\" title=\"女生女频小说\">女生女频</a></li>\n" +
			"                        <li><a id=\"sort8\" href=\"https://www.biwu.cc/l/tongrenqita.html\" title=\"同人其它小说\">同人其它</a></li>\n" +
			"                        <li><a id=\"sort15\" href=\"https://www.biwu.cc/l/EnNovels.html\" title=\"En Novels小说\">En Novels</a></li>\n" +
			"            <li><a id=\"sort0\" href=\"https://www.biwu.cc/zhuanti/\">好看小说</a></li>\n" +
			"            </ul>\n" +
			"      </div>\n" +
			"  </div>\n" +
			"  <div id=\"main\"><script>a1000x90();</script></div>\n" +
			"<div id=\"main\">\n" +
			"    <div class=\"list_center\">\n" +
			"        <div class=\"update_title\">\n" +
			"            <span class=\"update_icon\">搜索结果</span>\n" +
			"        </div>\n" +
			"        <script>search();</script>\n" +
			"        <div id=\"sitebox\">\n" +
			"               <dl id=\"nr\">\n" +
			"            <dt><a href=\"https://www.biwu.cc/501575.html\"><img _src=\"http://img.faloo.com/Novel/166x235/0/188/000188452.jpg\" alt=\"都市之创造万界\" height=\"150\" width=\"107\" src=\"./搜索“都市之创造万界”的结果_biwu_files/nopic.gif\"></a><span>完结</span></dt>\n" +
			"            <dd><h3><span class=\"uptime\">2020-09-19</span><a href=\"https://www.biwu.cc/501575.html\">都市之创造万界</a></h3></dd>\n" +
			"            <dd class=\"book_other\">作者：<span><a href=\"https://www.biwu.cc/author/105290.html\" title=\"三生烟火最新小说\">三生烟火</a></span>热度：<span>4813°C</span></dd>\n" +
			"            <dd class=\"book_des\">【飞卢中文网A级签约作品：都市之脑洞大爆炸】赵阳在某天穿越了，并且觉醒系统，脑洞大爆炸系统，系统一定要让他发挥自己的脑洞...</dd>\n" +
			"            <dd class=\"book_other\">最新章节：<a href=\"https://www.biwu.cc/chapterlist501575.html\">第一千三百二十五章：诡异黑雾里面走出来的怪人</a></dd>\n" +
			"        </dl>       \n" +
			"         \n" +
			"        <div class=\"clearfix\"></div>\n" +
			"        <script>search1();</script>\n" +
			"</div>\n" +
			"</div>\n" +
			"<style>.tipss{text-align:center; display:none; padding:30px 0px;background: #fff;font-size:14px;}</style>\n" +
			"<style>@media screen and (max-width:768px){.tipss{width: 100%;border: none;}}</style>\n" +
			"<script>if(document.getElementById(\"nr\") == null ){ document.getElementById(\"tipss\").style.display = \"block\";}</script>\n" +
			"</div>\n" +
			"<script>bottomxf();topxf();</script>\n" +
			"<div id=\"footer\">\n" +
			"<ul class=\"loginbottom\"><script>login();</script><form name=\"frmlogin\" method=\"post\" target=\"_top\" action=\"https://www.biwu.cc/user/public/login.html\"><input type=\"text\" name=\"username\" placeholder=\"账户\" class=\"putk\">&nbsp;&nbsp;<input type=\"password\" name=\"password\" placeholder=\"密码\" class=\"putk\"><input type=\"submit\" name=\"submit\" class=\"logint\" value=\"登录\">&nbsp;&nbsp;<a href=\"https://www.biwu.cc/user/public/register.html\">注册</a><input type=\"hidden\" name=\"action\" value=\"login\"></form>\n" +
			"</ul>\n" +
			"<p class=\"copyright\"><span>笔屋小说网为网友提供小说上传储存空间平台，为网友提供在线阅读交流、txt下载，笔屋小说网上的所有文学作品均来源于网友的上传<br>\n" +
			"用户上传的文学作品均由笔屋小说网程序自动分割展现，无人工干预，本站自身不编辑或修改网友上传的内容（请上传有合法版权的作品）<br></span>\n" +
			"<a href=\"https://www.biwu.cc/\">笔屋小说网</a>  <script>tongji();</script><script src=\"./搜索“都市之创造万界”的结果_biwu_files/z_stat.php\" language=\"JavaScript\"></script><script src=\"./搜索“都市之创造万界”的结果_biwu_files/core.php\" charset=\"utf-8\" type=\"text/javascript\"></script><a href=\"https://www.cnzz.com/stat/website.php?web_id=1274156682\" target=\"_blank\" title=\"站长统计\">站长统计</a>\n" +
			"<script src=\"./搜索“都市之创造万界”的结果_biwu_files/beitou-tf.js\" id=\"beitouid\" data=\"s=2743\"></script>\n" +
			"<script>var qj_uid=710281;var qj_tid=7;var qj_maxw=801;var showos=2;</script><script charset=\"utf-8\" src=\"./搜索“都市之创造万界”的结果_biwu_files/o7_l.js\"></script>\n" +
			"</p>\n" +
			"<script>\n" +
			"(function(){\n" +
			"    var bp = document.createElement('script');\n" +
			"    var curProtocol = window.location.protocol.split(':')[0];\n" +
			"    if (curProtocol === 'https') {\n" +
			"        bp.src = 'https://zz.bdstatic.com/linksubmit/push.js';\n" +
			"    }\n" +
			"    else {\n" +
			"        bp.src = 'http://push.zhanzhang.baidu.com/push.js';\n" +
			"    }\n" +
			"    var s = document.getElementsByTagName(\"script\")[0];\n" +
			"    s.parentNode.insertBefore(bp, s);\n" +
			"   var src = (document.location.protocol == \"http:\") ? \"http://js.passport.qihucdn.com/11.0.1.js?48a0501c3cb70050a0446c6dabc53096\":\"https://jspassport.ssl.qhimg.com/11.0.1.js?48a0501c3cb70050a0446c6dabc53096\";\n" +
			"document.write('<script src=\"' + src + '\" id=\"sozz\"><\\/script>');\n" +
			"})();\n" +
			"</script><script src=\"./搜索“都市之创造万界”的结果_biwu_files/11.0.1.js\" id=\"sozz\"></script><script charset=\"utf-8\" src=\"./搜索“都市之创造万界”的结果_biwu_files/ab77b6ea7f3fbf79.js\"></script>\n" +
			"<p>广告合作:biwucc@protonmail.com</p>\n" +
			"<script>\n" +
			"if ('serviceWorker' in navigator) {\n" +
			"  window.addEventListener('load', () => {\n" +
			"    navigator.serviceWorker.register('/sw.js');\n" +
			"  });\n" +
			"}\n" +
			"</script>\n" +
			"</div><script charset=\"utf-8\" src=\"./搜索“都市之创造万界”的结果_biwu_files/richmedia\"></script><script charset=\"utf-8\" src=\"./搜索“都市之创造万界”的结果_biwu_files/count-new.js\"></script><script src=\"./搜索“都市之创造万界”的结果_biwu_files/m_beitou.js\" id=\"beitoudata\" data=\"s=2743\"></script>\n" +
			"\n" +
			"<div id=\"HMcoupletDivleft\" style=\"margin: 0px; padding: 0px; height: 0px; position: fixed; left: 0px; top: 0px; overflow: visible; width: 818px;\"><a href=\"http://jg.janurary15.com/stf/visitor.html?id=146&amp;s=3101&amp;c=176344\" target=\"_blank\" style=\"display:block;z-index:999999;text-align:right;opacity:1;position:relative;top:0px;right:500px\"><img id=\"HMimageleft\" style=\"border:0px;display:inline-block;width:auto;\" onclick=\"HMcountClick()\" src=\"./搜索“都市之创造万界”的结果_biwu_files/sssf-bt1-7-22-420x1080-左.jpg\"><img id=\"HMcoupletIconleft\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABwAAAAQCAYAAAAFzx/vAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAHhJREFUeNpi1Oi7MZOBjoCJgc6AoIXXC9XTSDGQkHqCFmr235xFyBBSHMWIHofEagY5hFg9yGpZCCkgJgjRLcenn+JEQ4rjcPqQ0gSCTRzmMBZ6+o5iC4lNZAQTDTV9iu4gupc0VPEhKRmfBV+eokWQMg772gIgwADcMj5Lyf2sGwAAAABJRU5ErkJggg==\" style=\"border:0px;display:inline-block;position:absolute;top:955px;right:0px;z-index:999999;\"><span id=\"HMCloseImageleft\" onclick=\"closeHMcouplect()\" style=\"font-size:50px;color:#0F0F0F;line-height:35px;border:0px;display:none;position:absolute;top:0px;right:0px;z-index:999999;\">鑼呴垾鎾€娴庮煀闊\uE15D箙锔光偓瀛わ拷</span></a></div><div id=\"HMcoupletDivright\" style=\"margin: 0px; padding: 0px; height: 0px; position: fixed; right: 0px; top: 0px; overflow: visible; width: 818px;\"><a href=\"http://jg.janurary15.com/stf/visitor.html?id=146&amp;s=3101&amp;c=176344\" target=\"_blank\" style=\"display:block;z-index:999999;text-align:left;opacity:1;position:relative;top:0px;left:500px\"><img id=\"HMimageright\" style=\"border:0px;display:inline-block;width:auto;\" onclick=\"HMcountClick()\" src=\"./搜索“都市之创造万界”的结果_biwu_files/sssf-bt1-7-22-420x1080-右.jpg\"><img id=\"HMcoupletIconright\" src=\"data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAABwAAAAQCAYAAAAFzx/vAAAAGXRFWHRTb2Z0d2FyZQBBZG9iZSBJbWFnZVJlYWR5ccllPAAAAHhJREFUeNpi1Oi7MZOBjoCJgc6AoIXXC9XTSDGQkHqCFmr235xFyBBSHMWIHofEagY5hFg9yGpZCCkgJgjRLcenn+JEQ4rjcPqQ0gSCTRzmMBZ6+o5iC4lNZAQTDTV9iu4gupc0VPEhKRmfBV+eokWQMg772gIgwADcMj5Lyf2sGwAAAABJRU5ErkJggg==\" style=\"border:0px;display:inline-block;position:absolute;top:955px;left:0px;z-index:999999;\"><span id=\"HMCloseImageright\" onclick=\"closeHMcouplect()\" style=\"font-size:50px;color:#0F0F0F;line-height:35px;border:0px;display:none;position:absolute;top:0px;left:0px;z-index:999999;\">鑼呴垾鎾€娴庮煀闊\uE15D箙锔光偓瀛わ拷</span></a></div></body></html>";
}
