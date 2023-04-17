function delFruit(fid) {
    if (confirm("确认要删除吗")) {
        window.location.href = 'fruit.do?fid=' + fid + '&operate=del';
    }
}

function page(pageNo) {
    window.location.href = 'fruit.do?pageNo=' + pageNo;
}