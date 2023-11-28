const jsSHA = require('sha1.js')


function intToBytes2(n){
  var b = new Array(8);
  for (var i = 0; i < 4; i++)
  {
    var temp = n << (i*8) ;
    b[i+4] = (temp >>> 24);
  }
  b[0] = b[1] = b[2] = b[3] = 0;
  return b;
}
//十六进制字符串转字节数组
function Hex2Bytes(str) {
  var pos = 0;
  var len = str.length;
  if (len % 2 != 0) {
    return null;
  }
  len /= 2;
  var hexA = new Array();
  for (var i = 0; i < len; i++) {
    var s = str.substr(pos, 2);
    var v = parseInt(s, 16);
    hexA.push(v);
    pos += 2;
  }
  return hexA;
}

//字节数组转十六进制字符串
function Bytes2Hex(arr) {
  var str = "";
  for (var i = 0; i < arr.length; i++) {
    var tmp = arr[i].toString(16);
    if (tmp.length == 1) {
      tmp = "0" + tmp;
    }
    str += tmp;
  }
  return str;
}
// 通过TOTP算法获得结果
function getTOTPCode(secret) {
  var key = secret;
  //  1577808000  是 1970.1.1至2020年1月1日0点的秒数
   var time = Math.floor((new Date().getTime() / 1000 - 1577808000) / 180);
//  var time = Math.floor((1995826650625 / 1000 - 1577808000) / 180);
//  time = "11386";
// console.log(key +","+time);
  var shaObj = new jsSHA("SHA-1","HEX");
  shaObj.setHMACKey(key, "TEXT");
  // 通过int 转换为bytes 再转换为hex 进行算
  var by = intToBytes2(time);

//  var testHex = Bytes2Hex([0, 0, 0, 0, 0, 35, 111, 149]);
 // console.log(time + ":" + time.length + ":  " + testHex + ":  " + Bytes2Hex(by) +":  "  + by);

  shaObj.update(Bytes2Hex(by));
//  var hmac = shaObj.getHMAC("BYTES");
  var hex = shaObj.getHMAC("HEX");
//  console.log(hmac +","+ hmac.length);

  // var str = "";
  // for(var i = 0 ; i < hmac.length ; i++){
  //   str += hmac.charCodeAt(i)+",";
  // }
//  console.log(str);
  var hash = Hex2Bytes(hex);
//  console.log(hex);
 // console.log(hash);
  // 获取hash最后一个字节的低4位，作为选择结果的开始下标偏移
  var offset = hash[hash.length - 1] & 0xf;
//console.log(offset);

  // 获取4个字节组成一个整数，其中第一个字节最高位为符号位，不获取，使用0x7f
  var binary =
    ((hash[offset] & 0x7f) << 24) |
    ((hash[offset + 1] & 0xff) << 16) |
    ((hash[offset + 2] & 0xff) << 8) |
    (hash[offset + 3] & 0xff);
// console.log(binary);

  // 获取这个整数的后8位
  var otp = binary % 100000000;
  // 将数字转成字符串，不够8位前面补0
  var result = otp+"";
  while (result.length < 8) {
    result = "0" + result;
  }
  return result;
}
module.exports.getTOTPCode = getTOTPCode