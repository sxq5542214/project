var Long = require("./long.js");
var  DYPWD_KEY_UNITS_MAXSIZE =	(64 / 4) ;
var s_saved_private_key_units = new Array(DYPWD_KEY_UNITS_MAXSIZE);
var s_saved_private_key_units_size = 0;
/**
 * 用于构建密钥的位数组单元
 * @param {} key_units   存储key转化为字节表的单元数组，以key字节化的每4个位为一个单元
 * @param {*} key_string  具体的key值
 */
function build_key_units(key_units,key_string)
{
  var key_value;
  var i, key_units_size;
  var key_ch;

//  console.info("private_key: %s\n", key_string);
//  console.info("private_key: %s\n", key_string[0].charCodeAt());
  i = 0;
  key_value = 0;
  console.log("key_string :" + key_string)
  while ( i < key_string.length){
    key_ch = key_string[i++];
    key_value = (key_value * 10) + Number(key_ch.charCodeAt());
//console.info("key_value: %d\n", key_value);
  }


  key_units_size = 0;
  do {
    key_units_size++;

//console.info("key_value & 0x0f : %d\n", key_value & 0x0f);
//console.info("key_units_size : %d\n", key_units_size);
    key_units[DYPWD_V2_OTP_KEY_UNITS_MAXSIZE - key_units_size] =
      (key_value & 0x0f) ^ key_units_size;
//    console.info("key_units : %d\n", key_units[DYPWD_V2_OTP_KEY_UNITS_MAXSIZE - key_units_size]);
//    key_value = key_value >> 4;
    key_value = Long.fromString(key_value + "", true).shiftRight(4).toNumber() ;

//    console.info("key_value2: %d\n", key_value);
  } while (key_value > 0);

  if (key_units_size < DYPWD_V2_OTP_KEY_UNITS_MAXSIZE) {
    for (i = 0; i < key_units_size; i++)
      key_units[i] = key_units[i + DYPWD_V2_OTP_KEY_UNITS_MAXSIZE - key_units_size];
    while (i < DYPWD_V2_OTP_KEY_UNITS_MAXSIZE)
      key_units[i++] = 0;
  }

  show_units("build_key", key_units, key_units_size);
 
  return key_units_size;
}

function encrypt_data_units( data_units,
   data_units_size,  key_units,
   key_units_size) {

   var real_data_units_size, i;
   var key_id;

  real_data_units_size = data_units_size;

  for (i = 0; i < key_units_size; i++) {
    if (i < real_data_units_size) {
      data_units[i] += key_units[i];
      data_units[i] &= 0x0f;
    }
    xor_data_units(data_units, real_data_units_size, key_units[i]);
    rotate_left_shift(data_units, real_data_units_size, key_units[i] & 0x03);
  }

  key_id = get_check_value(key_units, key_units_size);
  data_units[real_data_units_size] = key_id;
  real_data_units_size++;
  show_units("encrypt", data_units, real_data_units_size);

  return real_data_units_size;
}

function xor_data_units(data_units, data_units_size,  xor_value)
{
	var i;
  if (!xor_value)
    xor_value = 0x0f;

  for (i = 0; i < data_units_size; i++)
    data_units[i] ^= xor_value;
  
  // if (xor_value) {
  //   for (i = 0; i < data_units_size; i++)
  //     data_units[i] ^= xor_value;
  // }
}


/* 0 <= shift < 0x10 */
function rotate_left_shift(units,units_size, shift)
{
  var shift_units_nums, shift_bits, i;
  var tmp = new Array(4);

  if (!shift)
    shift = 0x10;

  shift_units_nums = (shift >> 2) % units_size;
  if (shift_units_nums) {
    for (i = 0; i < shift_units_nums; i++)
    tmp[i] = units[i];
    while (i < units_size) {
      units[i - shift_units_nums] = units[i];
      i++;
    }
    while (shift_units_nums)
      units[--i] = tmp[--shift_units_nums];
  }

  shift_bits = shift & 3;
  if (shift_bits) {
    tmp[0] = units[0] >> (4 - shift_bits);
    units[0] = (units[0] << shift_bits) & 0x0f;
    for (i = 1; i < units_size; i++) {
      units[i - 1] |= units[i] >> (4 - shift_bits);
      units[i] = (units[i] << shift_bits) & 0x0f;
    }
    units[units_size - 1] |= tmp[0];
  }
}

function get_check_value(units, units_size)
{
  var check_value, i;
  check_value = 0;
  for (i = 0; i < units_size; i++)
    check_value += units[i] * (i + 1);
  while (check_value > 0x0f)
    check_value = (check_value >> 4) ^ (check_value & 0x0f);
  console.log(" 6 :  %d \n" , check_value );
  return check_value;
}
function confuse_data_units(data_units,  data_units_size)
{
	var confuse_value;

  /* get data_units check_value & take it as confuse value */
  confuse_value = get_check_value(data_units, data_units_size);

  /* rotate_left_shift & xor */
  rotate_left_shift(data_units, data_units_size, confuse_value & 0x03);
  xor_data_units(data_units, data_units_size, confuse_value);

  /* append the confuse value to the tail of the data_units */
  data_units[data_units_size] = confuse_value;

  return (data_units_size + 1);
}
function set_private_key_units(key_units, key_units_size)
{
  var i;
	for (i = 0; i < key_units_size; i++)
		s_saved_private_key_units[i] = key_units[i];
	s_saved_private_key_units_size = key_units_size;
}



var DYPWD_V2_OTP_YEAR_BASE = 2020
var DYPWD_V2_OTP_YEAR_MAX = 2036
var DYPWD_V2_OTP_MINUTE_STEP = 3

var DYPWD_V2_OTP_MINUTE_BITS = 5
var DYPWD_V2_OTP_HOUR_BITS = 5
var DYPWD_V2_OTP_DAY_BITS = 5
var DYPWD_V2_OTP_MONTH_BITS = 4
var DYPWD_V2_OTP_YEAR_BITS = 5
var DYPWD_V2_OTP_CHECK_BITS = 4

var DYPWD_V2_OTP_MINUTE_SHIFT = 0;
var DYPWD_V2_OTP_HOUR_SHIFT = (DYPWD_V2_OTP_MINUTE_SHIFT + DYPWD_V2_OTP_MINUTE_BITS);
var DYPWD_V2_OTP_DAY_SHIFT = (DYPWD_V2_OTP_HOUR_SHIFT + DYPWD_V2_OTP_HOUR_BITS);
var DYPWD_V2_OTP_MONTH_SHIFT = (DYPWD_V2_OTP_DAY_SHIFT + DYPWD_V2_OTP_DAY_BITS);
var DYPWD_V2_OTP_YEAR_SHIFT = (DYPWD_V2_OTP_MONTH_SHIFT + DYPWD_V2_OTP_MONTH_BITS);
var DYPWD_V2_OTP_CHECK_SHIFT = (DYPWD_V2_OTP_YEAR_SHIFT + DYPWD_V2_OTP_YEAR_BITS);
var DYPWD_V2_OTP_TOTAL_BITS = (DYPWD_V2_OTP_CHECK_SHIFT + DYPWD_V2_OTP_CHECK_BITS);

var DYPWD_V2_OTP_MINUTE_MASK = ((1 << DYPWD_V2_OTP_MINUTE_BITS) - 1);
var DYPWD_V2_OTP_HOUR_MASK = ((1 << DYPWD_V2_OTP_HOUR_BITS) - 1);
var DYPWD_V2_OTP_DAY_MASK = ((1 << DYPWD_V2_OTP_DAY_BITS) - 1);
var DYPWD_V2_OTP_MONTH_MASK = ((1 << DYPWD_V2_OTP_MONTH_BITS) - 1);
var DYPWD_V2_OTP_YEAR_MASK = ((1 << DYPWD_V2_OTP_YEAR_BITS) - 1);
var DYPWD_V2_OTP_CHECK_MASK = ((1 << DYPWD_V2_OTP_CHECK_BITS) - 1);


var DYPWD_V2_OTP_ORIGINAL_UNITS_SIZE = (DYPWD_V2_OTP_TOTAL_BITS >> 2);
var DYPWD_V2_OTP_ENCRYPT_UNITS_SIZE = (DYPWD_V2_OTP_ORIGINAL_UNITS_SIZE + 1);
var DYPWD_V2_OTP_CONFUSE_UNITS_SIZE = (DYPWD_V2_OTP_ENCRYPT_UNITS_SIZE + 1);
var DYPWD_V2_OTP_FINAL_UNITS_SIZE = DYPWD_V2_OTP_CONFUSE_UNITS_SIZE;


if (8 == DYPWD_V2_OTP_FINAL_UNITS_SIZE)
var DYPWD_V2_OTP_DIGITS_SIZE=	10;
if(9 == DYPWD_V2_OTP_FINAL_UNITS_SIZE)
var DYPWD_V2_OTP_DIGITS_SIZE=	12;

var DYPWD_V2_OTP_KEY_DIGITS_MINSIZE	=6;
var DYPWD_V2_OTP_KEY_DIGITS_MAXSIZE	=10;
var DYPWD_V2_OTP_KEY_UNITS_MAXSIZE	=16;


function arrayToString(array){
  var str = '';
  for(var i = 0 ; i < array.length; i++){
    str += array[i];
  }
  return str;
}


function generate_otp_password( pwd_digits, enc_material)
{
  var key_units = new Array(DYPWD_V2_OTP_KEY_UNITS_MAXSIZE);
  var data_units = new Array(DYPWD_V2_OTP_FINAL_UNITS_SIZE);
  var key_units_size, data_units_size;

  data_units_size = dypwd_v2_otp_build_units(data_units,
    enc_material);

  console.log("data_units_size :" + data_units_size);
  /* encrypt with private key & confuse */
  key_units_size = build_key_units(key_units,
    enc_material.key_string);

  console.log("key_units_size :" + key_units_size);

  if (!key_units_size)
    return -1;
  data_units_size = encrypt_data_units(data_units,
    data_units_size, key_units,
    key_units_size);

  data_units_size = confuse_data_units(data_units,
    data_units_size);
  show_units("confuse", data_units, data_units_size);

  dypwd_v2_otp_u2d(data_units, pwd_digits);
  show_units(" pwd_digits ", pwd_digits, pwd_digits.length);

  console.log("OTP: GEN - uuid(%d) seq(%d) key(%s), pwd(%s)\n",
    enc_material.uuid, enc_material.seq,
    enc_material.key_string, pwd_digits);

  return 0;
}

function dypwd_v2_otp_build_units( data_units, enc_material)
{
  var data_value, value_tmp, bit_offset;
  var unit_value, check_value;

  value_tmp = parseInt(enc_material.date_time.minute / DYPWD_V2_OTP_MINUTE_STEP);
  data_value = (value_tmp << DYPWD_V2_OTP_MINUTE_SHIFT)
    | (enc_material.date_time.hour << DYPWD_V2_OTP_HOUR_SHIFT)
					| (enc_material.date_time.day << DYPWD_V2_OTP_DAY_SHIFT)
					| (enc_material.date_time.month << DYPWD_V2_OTP_MONTH_SHIFT);
  value_tmp = enc_material.date_time.year - DYPWD_V2_OTP_YEAR_BASE;
  data_value |= value_tmp << DYPWD_V2_OTP_YEAR_SHIFT;

  check_value = 0;
  bit_offset = 0;
  while (bit_offset < DYPWD_V2_OTP_CHECK_SHIFT) {
    unit_value = ((data_value >> bit_offset) & 0x0f);
    data_units[bit_offset >> 2] = unit_value;
    check_value ^= unit_value;
    bit_offset += 4;
  }
  data_units[DYPWD_V2_OTP_CHECK_SHIFT >> 2] = check_value;

  rotate_left_shift(data_units, DYPWD_V2_OTP_CHECK_SHIFT >> 2,
    check_value & 0x03);
  xor_data_units(data_units, DYPWD_V2_OTP_CHECK_SHIFT >> 2,
    ~check_value & 0x0f);

  show_units("original", data_units, DYPWD_V2_OTP_ORIGINAL_UNITS_SIZE);

  return DYPWD_V2_OTP_ORIGINAL_UNITS_SIZE;

}

function dypwd_v2_otp_u2d(data_units,  digits)
{

  console.log("DYPWD_V2_OTP_DIGITS_SIZE :" + DYPWD_V2_OTP_DIGITS_SIZE);
  if (10 == DYPWD_V2_OTP_DIGITS_SIZE){
    var digits_size, units_offset;
  var value_0, value_1, value_2;

  units_offset = 0;
  digits_size = 0;
    while ((units_offset + 4) <= DYPWD_V2_OTP_FINAL_UNITS_SIZE) {
    value_0 = (data_units[units_offset] << 4) | data_units[units_offset + 1];
    value_1 = parseInt(value_0 / 100) * 3;
    value_0 %= 100;

    value_2 = (data_units[units_offset + 2] << 4) | data_units[units_offset + 3];
    value_1 += parseInt(value_2 / 100);
    value_2 %= 100;

    digits[digits_size] = parseInt(value_0 / 10) + '0';
    digits[digits_size + 1] = parseInt(value_0 % 10) + '0';
    digits[digits_size + 2] = value_1 + '0';
    digits[digits_size + 3] = parseInt(value_2 / 10) + '0';
    digits[digits_size + 4] = parseInt(value_2 % 10) + '0';

    units_offset += 4;
    digits_size += 5;
  }

  digits[digits_size] = '';
  } else if (12 == DYPWD_V2_OTP_DIGITS_SIZE){
  var value, value_combined;
  var digits_offset, units_offset;

  digits_offset = 0;
  units_offset = 0;
  value_combined = 0;
    while (units_offset < DYPWD_V2_OTP_FINAL_UNITS_SIZE) {
    value = (data_units[units_offset] << 8)
					| (data_units[units_offset + 1] << 4)
					| data_units[units_offset + 2];
    value_combined = (value_combined * 5) + parseInt(value / 1000);
    value %= 1000;
    digits[digits_offset++] = Number('0' + parseInt(value / 100));
    value %= 100;
    digits[digits_offset++] = Number('0' + parseInt(value / 10));
    digits[digits_offset++] = Number('0' + (value % 10));
    units_offset += 3;
  }

    console.log("value_combined : %d", value_combined);

    digits[digits_offset++] = Number('0' + parseInt(value_combined / 100));

  value_combined %= 100;
    digits[digits_offset++] = Number('0' + parseInt(value_combined / 10));

    digits[digits_offset++] = Number('0' + (value_combined % 10));

  digits[digits_offset] = '';
  }else{
    console.log("error .....");
  }

  console.log("U2D: %s\n", digits);
}

function show_units(prefix,units, units_size)
{
  var i;

  console.log("\n show_units    %s: %d", prefix, units_size);
 // for (i = 0; i < units_size; i++)
    console.log( units);
  console.log("\n");
}

/**
 * 生成次数密码
 * 传参为 密钥、UUID、次数
 */
function generateSEQPWD(key_str, date_time ) {
  var pwd_digits = new Array(DYPWD_V2_OTP_DIGITS_SIZE + 1);
  var enc_material = new Object();
  var old_ch, new_ch;
  var i, j, k;


  console.log("------------------------------------------------------------\n");


  /* test pswword generation */
  enc_material.key_string = key_str;
  enc_material.date_time = date_time ;
  // enc_material.date_time = new Object();
  // enc_material.date_time.year = 2021;
  // enc_material.date_time.month = 1;
  // enc_material.date_time.day = 1;
  // enc_material.date_time.hour = 13;
  // enc_material.date_time.minute = 17;
  // enc_material.date_time.second = 0;

    generate_otp_password(pwd_digits, enc_material);

  return arrayToString(pwd_digits);
}


// module.exports.build_key_units = build_key_units;
// module.exports.encrypt_data_units = encrypt_data_units;
// module.exports.build_edtq_data_units = build_edtq_data_units;
// module.exports.convert_units_to_digits = convert_units_to_digits ;
module.exports.generateSEQPWD = generateSEQPWD;