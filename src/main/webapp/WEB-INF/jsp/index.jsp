<%--
  Created by IntelliJ IDEA.
  User: dell
  Date: 2021/3/22
  Time: 17:19
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" type="text/css" href="css/app.css"/>
    <link rel="stylesheet" type="text/css" href="css/chunk.css">
    <link rel="stylesheet" type="text/css" href="css/index.css">
    <link rel="stylesheet" type="text/css" href="css/login_Home_Welcome.css">
    <link rel="stylesheet" type="text/css" href="css/iconfont.css">

    <!-- 引入 ECharts 文件 -->
    <script src="static/echarts.min.js"></script>
    <script src="static/jquery-3.5.1.min.js"></script>
    <script src="static/index.js"></script>
</head>
<body>
<div id="app" >
    <section class="el-container home-container is-vertical">
        <header class="el-header" style="height: 60px;">
            <div>
                <img src="images/购物车满.png">
                <span>基于Hadoop的购物网站分析系统</span>
            </div>
            <div>
                <div id="user-name">欢迎您：Cristina</div>
                <div>
                    <span class="screenfull-svg icon-expand-alt" onclick="toggleFullScreen()"></span>
                </div>
            </div>

        </header>
        <section class="el-container">
            <aside class="el-aside" style="width: 200px">
                <div class="toggle-button"> 目录 </div>
                <ul role="menubar" class="el-menu" style="background-color: rgb(51,55,68);">
                    <li role="menuitem" tabindex="-1" class="el-menu-item is-active" style="padding-left: 20px;color: rgb(64,158,255);background-color: rgb(51,55,68);">
                        <i class="iconfont iconhome">
                        </i>
                        <span>Welcome</span>
                    </li>

                    <li role="menuitem" aria-haspopup="true" class="el-submenu" id="custom">
                        <div class="el-submenu__title" style="padding-left: 20px;color: rgb(255,255,255);background-color: rgb(51,55,68);" onclick="show('second-menu')">
                            <i class="iconfont icondata">
                            </i>
                            <span>数据统计</span>
<%--                            <i class="el-submenu__icon-arrow el-icon-arrow-down"></i>--%>
                        </div>
                        <ul role="menu" class="el-menu el-menu--inline" id="second-menu" style="background-color: rgb(51,55,68);display: none;" data-old-padding-top data-old-padding-bottom data-old-overflow>
                            <li role="menuitem" tabindex="-1" class="el-menu-item" style="padding-left: 40px;color: rgb(255,255,255);background-color: rgb(51,55,68);" onclick="location.href = 'reports' ;">
                                <i class="iconfont icondata"></i>
                                <span>数据视图</span>
                            </li>
                        </ul>
                    </li>
                </ul>
            </aside>
            <main class="el-main">
                <div class="welcome">
                    <div class="el-row" style="margin-left: -6px;margin-right: -6px;">
                        <div class="el-col el-col-6" style="padding-left: 6px;padding-right: 6px;">
                            <div class="el-card is-always-shadow">
                                <div class="el-card__body">
                                    <div class="left" style>
                                        <span class="iconfont iconshopping" style="color: rgb(64,201,198);">
                                        </span>
                                    </div>
                                    <div class="right">
                                        <div class="title1" id="select_item1">页面浏览总量</div>
                                        <div class="data" id="pvtotal"></div>
                                        <script type="text/javascript">showtotalpvs();</script>
                                    </div>
                                </div>

                            </div>
                        </div>

                        <div class="el-col el-col-6" style="padding-left: 6px;padding-right: 6px;">
                            <div class="el-card is-always-shadow">
                                <div class="el-card__body" id="select_item2">
                                    <div class="left" >
                                        <span class="iconfont iconshopping" style="color: rgb(54,163,247);">
                                        </span>
                                    </div>
                                    <div class="right">
                                        <div class="title1">独立访客总数</div>
                                        <div class="data" id="iptotal"></div>
                                        <script type="text/javascript">showtotalip();</script>
                                    </div>
                                </div>

                            </div>
                        </div>
                        <div class="el-col el-col-6" style="padding-left: 6px;padding-right: 6px;">
                            <div class="el-card is-always-shadow">
                                <div class="el-card__body" id="select_item3">
                                    <div class="left" style>
                                        <span class="iconfont iconshopping" style="color: rgb(244,81,108);">
                                        </span>
                                    </div>
                                    <div class="right">
                                        <div class="title1">人均访问的频次</div>
                                        <div class="data" id="avgVisit"></div>
                                        <script type="text/javascript">showavgVisit();</script>
                                    </div>
                                </div>

                            </div>
                        </div>
                        <div class="el-col el-col-6" style="padding-left: 6px;padding-right: 6px;">
                            <div class="el-card is-always-shadow">
                                <div class="el-card__body" id="select_item4">
                                    <div class="left" style>
                                        <span class="iconfont iconshopping" style="color: rgb(52,191,163);">
                                        </span>
                                    </div>
                                    <div class="right">
                                        <div class="title1">人均页面浏览量</div>
                                        <div class="data" id="avgPv"></div>
                                        <script type="text/javascript">showavgPv();</script>
                                    </div>
                                </div>

                            </div>
                        </div>
                    </div>

                    <div class="el-card is-always-shadow" id="line">
                        <div class="el-card__body">
                            <div style="height: 400px; -webkit-tap-highlight-color: transparent;user-select: none;position: relative">
                                <div id="main1" style="width: 1234px;height:400px;"></div>

                            </div>
                            <script type="text/javascript">showechart1();</script>

                        </div>
                    </div>
                </div>

            </main>
        </section>
    </section>

</div>

</body>
</html>
