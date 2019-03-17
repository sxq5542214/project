<%@page import="com.yd.business.product.bean.SupplierProductBean"%>
<%@page import="com.yd.business.user.bean.UserWechatBean"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
	List<SupplierProductBean> list = (List<SupplierProductBean>)request.getAttribute("supplierProductList");
	UserWechatBean user = (UserWechatBean) request.getAttribute("user");
	if(list == null){
		list = Collections.EMPTY_LIST;
	}
%>
<!DOCTYPE html>
<html lang="zh-CN">
<head>
    <meta charset="utf-8">
    <base href="<%=basePath%>">
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>坚果特卖汇-专注于提供优质坚果</title><meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport"
	content="width=device-width,initial-scale=1,minimum-scale=1,maximum-scale=1,user-scalable=0,minimal-ui,viewport-fit=cover">
<meta name="description"
	content="坚果特卖汇-专注于提供优质坚果">
<meta name="keywords"
	content="坚果,核桃,开心果,核桃仁,夏威夷果">
    <script src="js/user/supplierProductShop/rem.js"></script>    
    <link href="css/user/supplierProductShop/iconfont.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/user/supplierProductShop/base.css"/>
    <link href="css/user/supplierProductShop/mui.min.css" rel="stylesheet">
    <link href="css/user/supplierProductShop/all.css" rel="stylesheet">
    <link rel="stylesheet" type="text/css" href="css/user/supplierProductShop/style.css"/>
    <style type="text/css">
    	*{ margin: 0;}
    </style>
</head>
<body>
    <header class="mui-bar mui-bar-nav" id="header">
        <div class="top-sch-box flex-col">
            <div class="centerflex">
                <i class="fdj iconfont icon-search"></i>
                <div class="sch-txt">每天吃点坚果，享受健康生活</div>
                <span class="shaomiao"><i class="iconfont icon-saoma"></i></span>
            </div>
        </div>
        <a class="btn" href="">
            <i class="iconfont icon-tixing"></i>
        </a>
        <a class="btn" href="shopcar.html">
            <i class="iconfont icon-cart"></i>
        </a>
    </header>

<div id="main" class="mui-clearfix">
	 <!-- 搜索层 -->
    <div class="pop-schwrap">
        <div class="ui-scrollview">
            <div class="mui-bar mui-bar-nav clone">
                <a class="btn btn-back" href="javascript:;"></a>
                <div class="top-sch-box flex-col">
                    <div class="centerflex">
                        <input class="sch-input mui-input-clear" type="text" name="" id="" placeholder="连衣裙就是你的女人味儿" />
                    </div>
                </div>
                <a class="mui-btn mui-btn-primary sch-submit" href="search.html">搜索</a>
            </div>
            <div class="scroll-wrap">
                <div class="mui-scroll">
                    <div class="sch-cont">
                        <div class="section ui-border-b">
                            <div class="tit"><i class="iconfont icon-hot"></i>热门搜索</div>
                            <div class="tags">
                                <span class="tag actice">山核桃仁</span><span class="tag">手剥山核桃</span><span class="tag">碧根果</span><span class="tag">杏仁</span>
                                <span class="tag actice">开心果</span><span class="tag">夏威夷果</span><span class="tag">开口松子</span>
                            </div>
                        </div>
                     <!--    <div class="section">
                            <div class="tit"><i class="iconfont icon-time"></i>最近搜索</div>
                            <div class="tags">
                                <span class="tag">外套</span><span class="tag">连衣裙</span><span class="tag">运动鞋</span><span class="tag">睡衣</span>
                                <span class="tag">外套</span><span class="tag">连衣裙</span><span class="tag">运动鞋</span><span class="tag">睡衣</span>
                                <span class="tag">外套</span><span class="tag">连衣裙</span><span class="tag">运动鞋</span>
                            </div>
                        </div> -->
                    </div>
                    <!-- <div class="sch-clear">
                        <a href="javascript:void"><i class="iconfont icon-shanchu"></i>清除搜索历史</a>
                    </div> -->
                </div>
            </div>
        </div>
    </div>

    <div class="mui-content">
        <div class="banner swiper-container">
            <div class="swiper-wrapper">
                <div class="swiper-slide"><a href="javascript:void(0)"><img class="swiper-lazy" data-src="images/user/supplierProductShop/banner1.jpg" alt=""></a></div>
                <div class="swiper-slide"><a href="javascript:void(0)"><img class="swiper-lazy" data-src="images/user/supplierProductShop/banner1.jpg" alt=""></a></div>
                <div class="swiper-slide"><a href="javascript:void(0)"><img class="swiper-lazy" data-src="images/user/supplierProductShop/banner1.jpg" alt=""></a></div>
                <div class="swiper-slide"><a href="javascript:void(0)"><img class="swiper-lazy" data-src="images/user/supplierProductShop/banner1.jpg" alt=""></a></div>
            </div>
            <div class="swiper-pagination"></div>
        </div>

<!--         <div class="home-nav ui-box">
            <div class="ui-flex flex-justify-sb">
                <div><a href=""><img src="images/user/supplierProductShop/homenav1.png" alt="" /></a></div>
                <div><a href=""><img src="images/user/supplierProductShop/homenav2.png" alt="" /></a></div>
                <div><a href=""><img src="images/user/supplierProductShop/homenav3.png" alt="" /></a></div>
                <div><a href=""><img src="images/user/supplierProductShop/homenav4.png" alt="" /></a></div>
            </div>
        </div> -->

<!--         <div class="home-qnav ui-box">
            <div class="ui-flex flex-justify-sb">
                <div><a href="http://www.591zq.com"><img src="images/user/supplierProductShop/qnav1.png" class="ico" /><span class="name">我的店铺</span></a></div>
                <div><a href="http://www.591zq.com"><img src="images/user/supplierProductShop/qnav2.png" class="ico" /><span class="name">招商加盟</span></a></div>
                <div><a href="http://www.591zq.com"><img src="images/user/supplierProductShop/qnav3.png" class="ico" /><span class="name">我的喜欢</span></a></div>
                <div><a href="http://www.591zq.com"><img src="images/user/supplierProductShop/qnav4.png" class="ico" /><span class="name">猜你喜欢</span></a></div>
            </div>
        </div> -->

        <div class="home-newgoods ui-box">
            <img class="home-imgtit" src="images/user/supplierProductShop/hometit1.jpg" alt="" />
            <div class="list-type1 plist-puzzle">
                <a class="b" href=""><img src="images/user/supplierProductShop/t1.jpg" alt="" /></a>
                <div class="s ui-flex-vt flex-justify-sb">
                    <a class="box" href=""><img src="images/user/supplierProductShop/t2.jpg" alt="" /></a>
                    <a class="box" href=""><img src="images/user/supplierProductShop/t2.jpg" alt="" /></a>
                    <a class="box" href=""><img src="images/user/supplierProductShop/t2.jpg" alt="" /></a>
                </div>
            </div>
            <img class="home-imgtit" src="images/user/supplierProductShop/hometit2.jpg" alt="" />
            <div class="list-type2 ui-flex flex-justify-sb">
                <a class="box" href=""><img class="figure" src="images/user/supplierProductShop/t3.jpg" alt="" /><span class="tit">情侣穿搭</span></a>
                <a class="box" href=""><img class="figure" src="images/user/supplierProductShop/t3.jpg" alt="" /><span class="tit">约会美搭</span></a>
                <a class="box" href=""><img class="figure" src="images/user/supplierProductShop/t3.jpg" alt="" /><span class="tit">全部新款</span></a>
            </div>
        </div>

        <div class="home-fashion ui-box ui-border-t">
            <img class="home-imgtit" src="images/user/supplierProductShop/hometit3.jpg" alt="" />
	      <a href=""><img class="db margin-b-s" src="images/user/supplierProductShop/t4.jpg" width="100%" alt="" /></a>
            <div class="fastion-plist mui-row">
            
            <% for(int i = 0 ; i <list.size();i++){
            	SupplierProductBean prod = list.get(i);
             %>
            
                <div class="mui-col-xs-6">
                    <a class="item">
                        <img src="<%=prod.getHead_img() %>" alt="" class="figure" />
                        <span class="tit2"><span style="color: red;font-size:0.5rem;">	<%=prod.getProduct_price()/100d %>元 </span> / <del style="color: #999;"><%=prod.getProduct_real_price()/100d %>元</del><br>
                        <%=prod.getProduct_title() %>
                        
                        </span>
                    </a>
                </div>
            <%} %>
            
                <div class="mui-col-xs-6">
                    <a class="item">
                        <img src="images/user/supplierProductShop/t5.jpg" alt="" class="figure" /><span class="tit2">印花卫衣</span>
                    </a>
                </div>
                <div class="mui-col-xs-6">
                    <a class="item">
                        <img src="images/user/supplierProductShop/t5.jpg" alt="" class="figure" /><span class="tit2" >印花卫衣asdfasdfsadfa<br>dsfaaaaaaafasadfasdfsadfasdfsdf</span>
                    </a>
                </div>
                  <div class="mui-col-xs-6">
                    <a class="item">
                        <img src="images/user/supplierProductShop/t5.jpg" alt="" class="figure" /><span class="tit2">印花卫衣</span>
                    </a>
                </div>
                <div class="mui-col-xs-6">
                    <a class="item">
                        <img src="images/user/supplierProductShop/t5.jpg" alt="" class="figure" /><span class="tit2">印花卫衣asdfasdfsadfa<br>dsfaaaaaaafasadfasdfsadfasdfsdf</span>
                    </a>
                </div>
                
                
            </div>
        </div>
    </div> <!--mui-content end-->
</div>
   

    <footer class="page-footer fixed-footer" id="footer">
		<ul style="padding-left: 0px;">
			<li class="active" style="margin: 0;">
				<a href="index.html">
					<img src="images/user/supplierProductShop/footer01.png"/>
					<p>首页</p>
				</a>
			</li>
			<li>
				<a href="assort.html">
					<img src="images/user/supplierProductShop/footer002.png"/>
					<p>分类</p>
				</a>
			</li>
			<li>
				<a href="shopcar.html">
					<img src="images/user/supplierProductShop/footer003.png"/>
					<p>购物车</p>
				</a>
			</li>
			<li>
				<a href="self.html">
					<img src="images/user/supplierProductShop/footer004.png"/>
					<p>个人中心</p>
				</a>
			</li>
		</ul>
	</footer>

</body>
<script type="text/javascript" src="js/user/supplierProductShop/jquery-1.8.3.min.js" ></script>
<script src="js/user/supplierProductShop/fastclick.js"></script>
<script src="js/user/supplierProductShop/mui.min.js"></script>
<script type="text/javascript" src="js/user/supplierProductShop/hmt.js" ></script>
<!--插件-->
<link rel="stylesheet" href="js/user/supplierProductShop/swiper/swiper.min.css">
<script src="js/user/supplierProductShop/swiper/swiper.jquery.min.js"></script>
<!--插件-->
<script src="js/user/supplierProductShop/global.js"></script>
<script >
    $(function () {
        var banner = new Swiper('.banner',{
            autoplay: 5000,
            pagination : '.swiper-pagination',
            paginationClickable: true,
            lazyLoading : true,
            loop:true
        });

         mui('.pop-schwrap .sch-input').input();
        var deceleration = mui.os.ios?0.003:0.0009;
         mui('.pop-schwrap .scroll-wrap').scroll({
                bounce: true,
                indicators: true,
                deceleration:deceleration
        });
        $('.top-sch-box .fdj,.top-sch-box .sch-txt,.pop-schwrap .btn-back').on('click',function () {
            $('html,body').toggleClass('holding');
            $('.pop-schwrap').toggleClass('on');
            if($('.pop-schwrap').hasClass('on')) {;
                $('.pop-schwrap .sch-input').focus();
            }
        });

    });
</script>
</html>