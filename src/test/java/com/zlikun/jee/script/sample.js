/**
 * 函数参数由调用时传入
 * @param x
 * @param y
 * @returns {number}
 */
function formula(x, y) {
    // 全局变量 factor 由 Bindings 对象传入
    return factor * (x + y);
}