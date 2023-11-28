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

  console.info("private_key: %s\n", key_string);
  console.info("private_key: %s\n", key_string[0].charCodeAt());
  var key_num ;
  i = 0;
  key_value = 0;
  for(i = 0; i < key_string.length;i++){
    key_ch = key_string[i];

    key_num = key_ch.charCodeAt() ;
    key_value = (key_value * 10) + key_num;
  }

  key_units_size = new Uint8Array(1)[0];
  do {
    key_units_size++;
    key_units[DYPWD_KEY_UNITS_MAXSIZE - key_units_size] =
      (key_value & 0x0f) ^ key_units_size;
    key_value >>= 4;
  } while (key_value);

  if (key_units_size < DYPWD_KEY_UNITS_MAXSIZE) {
    for (i = 0; i < key_units_size; i++){

      key_units[i] = key_units[i + DYPWD_KEY_UNITS_MAXSIZE - key_units_size];
    }

    while (i < DYPWD_KEY_UNITS_MAXSIZE)
      key_units[i++] = 0;
  }


  return key_units_size;
}

function  encrypt_data_units( data_units,data_units_size,key_units,
  key_units_size, is_private_key)
{
	var real_data_units_size, i;
	var key_id;
	real_data_units_size = data_units_size;

	for (i = 0; i < key_units_size; i++) {
		xor_data_units(data_units, real_data_units_size, key_units[i]);
		rotate_left_shift(data_units, real_data_units_size, 1);
	}
	show_units("encrypt", data_units, real_data_units_size);

	if (is_private_key) {
		key_id = get_check_value(key_units, key_units_size);
		data_units[real_data_units_size] = key_id;
		real_data_units_size++;
		console.log("encrypt_index: key_id = %x\n", key_id);

		confuse_data_units(data_units, real_data_units_size);
		real_data_units_size++;
		show_units("encrypt_confuse", data_units, real_data_units_size);

		set_private_key_units(key_units, key_units_size);
	}
}
function xor_data_units(data_units, data_units_size,  xor_value)
{
	var i;

	if (!xor_value)
		xor_value = 0x0f;
	for (i = 0; i < data_units_size; i++)
		data_units[i] ^= xor_value;
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
console.log("confuse_value : %d " , confuse_value) ;

	/* rotate_left_shift & xor */
  rotate_left_shift(data_units, data_units_size, confuse_value);

console.log(data_units) ;
	xor_data_units(data_units, data_units_size, confuse_value);

	/* append the confuse value to the tail of the data_units */
	data_units[data_units_size] = confuse_value;
}
function set_private_key_units(key_units, key_units_size)
{
  var i;
	for (i = 0; i < key_units_size; i++)
		s_saved_private_key_units[i] = key_units[i];
	s_saved_private_key_units_size = key_units_size;
}

// 时间段的常量定义
var DYPWD_TYPE_EDTQ = 0;
var DYPWD_YEAR_BASE	= 2020;
var DYPWD_YEAR_DIFF_MAX =	20;
var DYPWD_EDTQ_ET_STEPS_BITS	= 6;
var DYPWD_EDTQ_ST_STEPS_BITS =	6;
var DYPWD_EDTQ_ED_DAY_BITS=	5;
var  DYPWD_EDTQ_ED_MONTH_BITS=	4;
var  DYPWD_EDTQ_ED_YEARS_DIFF_BITS=	5;
var  DYPWD_EDTQ_TYPE_BITS=	2;

var DYPWD_ORIGINAL_UNITS_SIZE =	7;
var DYPWD_ORIGINAL_CONFUSE_UNITS_SIZE =	(DYPWD_ORIGINAL_UNITS_SIZE + 1);
var DYPWD_ENCRYPT_PRI_UNITS_SIZE=	(DYPWD_ORIGINAL_CONFUSE_UNITS_SIZE + 1);
var DYPWD_ENCRYPT_PRI_CONFUSE_UNITS_SIZE=	(DYPWD_ENCRYPT_PRI_UNITS_SIZE + 1);
var DYPWD_ENCRYPT_PUB_UNITS_SIZE=	DYPWD_ENCRYPT_PRI_CONFUSE_UNITS_SIZE;
var DYPWD_FINAL_UNITS_SIZE=	DYPWD_ENCRYPT_PUB_UNITS_SIZE;
var DYPWD_PASSWORD_DIGITS=	13;

var DYPWD_ENCRYPT_PRI_CONFUSE_UNITS_SIZE = 10;
var sc_dypwd_public_key_units = new Array(0x04, 0x07, 0x04, 0x0B, 0x05, 0x04, 0x04, 0x0B );
var sc_dypwd_public_key_units_size = sc_dypwd_public_key_units.length ;

var  DYPWD_EDTQ_ET_STEPS_SHIFT=	0;
var  DYPWD_EDTQ_ST_STEPS_SHIFT=	(DYPWD_EDTQ_ET_STEPS_SHIFT + DYPWD_EDTQ_ET_STEPS_BITS);
var  DYPWD_EDTQ_ED_DAY_SHIFT	=	(DYPWD_EDTQ_ST_STEPS_SHIFT + DYPWD_EDTQ_ST_STEPS_BITS);
var  DYPWD_EDTQ_ED_MONTH_SHIFT=	(DYPWD_EDTQ_ED_DAY_SHIFT + DYPWD_EDTQ_ED_DAY_BITS);
var  DYPWD_EDTQ_ED_YEARS_DIFF_SHIFT=	(DYPWD_EDTQ_ED_MONTH_SHIFT + DYPWD_EDTQ_ED_MONTH_BITS);
var  DYPWD_EDTQ_TYPE_SHIFT=	(DYPWD_EDTQ_ED_YEARS_DIFF_SHIFT + DYPWD_EDTQ_ED_YEARS_DIFF_BITS);

// 日期段的常量定义
var DYPWD_TYPE_SDED = 1;
var DYPWD_SDED_ED_DAYS_DIFF_BITS = 12;
var DYPWD_SDED_ED_DAYS_DIFF_SHIFT = 0 ;
var DYPWD_SDED_SD_DAY_SHIFT = DYPWD_SDED_ED_DAYS_DIFF_SHIFT + DYPWD_SDED_ED_DAYS_DIFF_BITS;
var DYPWD_SDED_SD_MONTH_SHIFT=	(DYPWD_SDED_SD_DAY_SHIFT + DYPWD_EDTQ_ED_DAY_BITS);
var DYPWD_SDED_SD_YEARS_DIFF_SHIFT=	(DYPWD_SDED_SD_MONTH_SHIFT + DYPWD_EDTQ_ED_MONTH_BITS);
var DYPWD_SDED_TYPE_SHIFT = DYPWD_SDED_SD_YEARS_DIFF_SHIFT + DYPWD_EDTQ_ED_YEARS_DIFF_BITS;

/**
 * 根据给定日期、开始结束时间，创建加密的数组，以每4位一个单元
 * @param {根据} data_units 
 * @param {*} expire_date 
 * @param {*} start_time 
 * @param {*} end_time 
 */
function build_edtq_data_units(data_units,expire_date,start_time, end_time)
{
	var data_value, time_steps;
	var shift, mask, i;

	console.log("edtq: (%u-%02u-%02u) - (%02u:%02u - %02u:%02u)\n",
		expire_date.year, expire_date.month,
		expire_date.day, 
		start_time.hour, start_time.minute,
		end_time.hour, end_time.minute);

  //类型标识位
	data_value = DYPWD_TYPE_EDTQ << DYPWD_EDTQ_TYPE_SHIFT;

	data_value |= (expire_date.year - DYPWD_YEAR_BASE) << DYPWD_EDTQ_ED_YEARS_DIFF_SHIFT;
	data_value |= expire_date.month << DYPWD_EDTQ_ED_MONTH_SHIFT;
	data_value |= expire_date.day << DYPWD_EDTQ_ED_DAY_SHIFT;

	time_steps = (start_time.hour * 2) + (start_time.minute / 30);
	data_value |= time_steps << DYPWD_EDTQ_ST_STEPS_SHIFT;

	time_steps = (end_time.hour * 2) + (end_time.minute / 30);
	data_value |= time_steps << DYPWD_EDTQ_ET_STEPS_SHIFT;

//	edtq_show_all_fields(data_value);
console.log("4  %d\n 5: ", data_value);
	for (i = 0; i < DYPWD_ORIGINAL_UNITS_SIZE; i++) {
		shift = (DYPWD_ORIGINAL_UNITS_SIZE - 1 - i) * 4;
		mask = 0x0f << shift;
		data_units[i] = ((data_value & mask) >> shift);

	}
	show_units("edtq_original", data_units, DYPWD_ORIGINAL_UNITS_SIZE);

	confuse_data_units(data_units, DYPWD_ORIGINAL_UNITS_SIZE);
	show_units("edtq_confuse", data_units, DYPWD_ORIGINAL_CONFUSE_UNITS_SIZE);
}

/**
 * 根据给定开始日期、结束日期，创建加密的数组，以每4位一个单元
 * @param {根据} data_units 
 * @param {*} start_date 
 * @param {*} end_date 
 */
function build_sded_data_units(data_units,start_date, end_date)
{
	var data_value, ed_days_diff;
	var shift,mask, i;

console.log("edtq: start_date(%d-%d-%d)\n",
		start_date.year, start_date.month,
		start_date.day);
console.log("edtq: end_date(%s)\n",
		end_date);

	data_value = DYPWD_TYPE_SDED << DYPWD_SDED_TYPE_SHIFT;

	data_value |= (start_date.year - DYPWD_YEAR_BASE) << DYPWD_SDED_SD_YEARS_DIFF_SHIFT;
	data_value |= start_date.month << DYPWD_SDED_SD_MONTH_SHIFT;
	data_value |= start_date.day << DYPWD_SDED_SD_DAY_SHIFT;

	ed_days_diff =  dateSubtraction(start_date, end_date);
	data_value |= ed_days_diff << DYPWD_SDED_ED_DAYS_DIFF_SHIFT;
console.log("data_value : " , data_value);

  for (i = 0; i < DYPWD_ORIGINAL_UNITS_SIZE; i++) {
    shift = (DYPWD_ORIGINAL_UNITS_SIZE - 1 - i) * 4;
    mask = 0x0f << shift;
    data_units[i] = ((data_value & mask) >> shift);

  }
  // mask = 0x0f << ((DYPWD_ORIGINAL_UNITS_SIZE - 1) * 4);
	// for (i = 0; i < DYPWD_ORIGINAL_UNITS_SIZE; i++)
	// 	data_units[i] = (data_value & mask) >> ((DYPWD_ORIGINAL_UNITS_SIZE - 1 - i) * 4);
show_units("sded_original", data_units, DYPWD_ORIGINAL_UNITS_SIZE);

	confuse_data_units(data_units, DYPWD_ORIGINAL_UNITS_SIZE);
show_units("sded_confuse", data_units, DYPWD_ORIGINAL_CONFUSE_UNITS_SIZE);
}


function convert_units_to_digits(digits,data_units)
{
	var digits_size, offset;
	var value_0, value_1, value_2;

	offset = 0;
	digits_size = 0;
	while ((offset + 4) <= DYPWD_FINAL_UNITS_SIZE) {
		value_0 = (data_units[offset] << 4) | data_units[offset + 1];
		value_1 = parseInt(value_0 / 100) * 3;
		value_0 %= 100;

		value_2 = (data_units[offset + 2] << 4) | data_units[offset + 3];
		value_1 += parseInt(value_2 / 100);
		value_2 %= 100;

		digits[digits_size] = parseInt(value_0 / 10) ;
		digits[digits_size + 1] = (value_0 % 10) ;
		digits[digits_size + 2] = value_1 ;
		digits[digits_size + 3] = parseInt(value_2 / 10) ;
		digits[digits_size + 4] = (value_2 % 10) ;

		offset += 4;
		digits_size += 5;
	}

	value_0 = (data_units[offset] << 4) | data_units[offset + 1];
	digits[digits_size] = parseInt((value_0 % 100) / 10) ;
	digits[digits_size + 1] = parseInt(value_0 / 100) ;
	digits[digits_size + 2] = (value_0 % 10) ;
	digits_size += 3;


console.log("Digits: %o " , digits);
}

/**
 *  根据密钥和时间要求，生成密码
 * @param {*} key_str 
 * @param {*} expire_date 
 * @param {*} start_time 
 * @param {*} end_time 
 */
function generateTimeSlotPWD(key_str,expire_date,start_time,end_time){
  
  var key_units = new Array();
  var key_units_size = build_key_units(key_units, key_str);
  console.log("key_units_size");
  console.log(key_units_size);
  console.log(key_units);

  var data_units = new Array();
  build_edtq_data_units(data_units,expire_date,start_time,end_time);
// 私钥加密
  encrypt_data_units(data_units,
    DYPWD_ORIGINAL_CONFUSE_UNITS_SIZE,      key_units, key_units_size, 1);
  console.log("encrypt_data_units1 : %o ",data_units);

  // 公钥加密
  /* encrypt with public key & don't confuse*/

  console.log("encrypt_data_units0 : %o  %d  %d  ",sc_dypwd_public_key_units , DYPWD_ENCRYPT_PRI_CONFUSE_UNITS_SIZE , sc_dypwd_public_key_units_size);

  encrypt_data_units(data_units,
    DYPWD_ENCRYPT_PRI_CONFUSE_UNITS_SIZE,
    sc_dypwd_public_key_units, sc_dypwd_public_key_units_size, 0);
  console.log("encrypt_data_units0 : %o ",data_units);

  var digits = new Array();
  convert_units_to_digits(digits,data_units);
  console.log("digits : %o" , digits);
  return arrayToString(digits);
}

function generateDateSlotPWD(key_str,startDate,endDate){
  
  var key_units = new Array();
  var key_units_size = build_key_units(key_units, key_str);
console.log("key_units : %o " , key_units);

  var data_units = new Array();
  build_sded_data_units(data_units,startDate,endDate);
  encrypt_data_units(data_units,
    DYPWD_ORIGINAL_CONFUSE_UNITS_SIZE,      key_units, key_units_size, 1);
  console.log("encrypt_data_units1 : %o ",data_units);

  // 公钥加密
  /* encrypt with public key & don't confuse*/

  console.log("encrypt_data_units0 : %o  %d  %d  ",sc_dypwd_public_key_units , DYPWD_ENCRYPT_PRI_CONFUSE_UNITS_SIZE , sc_dypwd_public_key_units_size);

  encrypt_data_units(data_units,
    DYPWD_ENCRYPT_PRI_CONFUSE_UNITS_SIZE,
    sc_dypwd_public_key_units, sc_dypwd_public_key_units_size, 0);
  console.log("encrypt_data_units0 : %o ",data_units);

  var digits = new Array();
  convert_units_to_digits(digits,data_units);
console.log("digits : %o" , digits);
  return arrayToString(digits);
}

function arrayToString(array){
  var str = '';
  for(var i = 0 ; i < array.length; i++){
    str += array[i];
  }
  return str;
}

function dateSubtraction(date1,date2){//获得天数
  //date1：开始日期，date2结束日期
  var a1 = Date.parse(new Date(date1));
  var a2 = Date.parse(new Date(date2));
  var day = parseInt((a2-a1)/ (1000 * 60 * 60 * 24));//核心：时间戳相减，然后除以天数
  return day
}

function show_units(prefix,units, units_size)
{
  var i;

  console.log("%s: %d", prefix, units_size);
 // for (i = 0; i < units_size; i++)
    console.log( units);
  console.log("\n");
}

// module.exports.build_key_units = build_key_units;
// module.exports.encrypt_data_units = encrypt_data_units;
// module.exports.build_edtq_data_units = build_edtq_data_units;
// module.exports.convert_units_to_digits = convert_units_to_digits ;
module.exports.generateTimeSlotPWD = generateTimeSlotPWD;
module.exports.generateDateSlotPWD = generateDateSlotPWD;