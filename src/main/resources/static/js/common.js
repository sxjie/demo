$(".sidebar-list > .parent > a").click(function () {
    $(this).next().slideToggle(200);
    $(this).parent().siblings().find('a').siblings().hide(200);
    $('.parent').removeClass('active');
    $(this).parent().addClass('active');
    return false;
});