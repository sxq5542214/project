var Long = require("./long.js");


var USER_DYNPWD_KEY_DIGITS_MAXSIZE=	10
var USER_DYNPWD_KEY_UNITS_MAXSIZE=	16
var USER_DYNPWD_DATA_UNITS_MAXSIZE=	16
var USER_DYNPWD_DIGITS_MAXSIZE=	15

var USER_DYNPWD_BASE_YEAR=	2020

var USER_DYNPWD_TYPE_BITS=	2
var USER_DYNPWD_FLAG_CANCEL_BITS=	1
var USER_DYNPWD_GENERAL_BITS=
	(USER_DYNPWD_TYPE_BITS + USER_DYNPWD_FLAG_CANCEL_BITS)

var USER_DYNPWD_GENERAL_SHIFT=	0

var USER_DYNPWD_TYPE_SHIFT=	USER_DYNPWD_GENERAL_SHIFT
var USER_DYNPWD_TYPE_MASK	=
	((1 << USER_DYNPWD_TYPE_BITS) - 1)

var USER_DYNPWD_FLAG_CANCEL_SHIFT=
	(USER_DYNPWD_TYPE_SHIFT + USER_DYNPWD_TYPE_BITS)
var USER_DYNPWD_FLAG_CANCEL_MASK	=
	(1 << USER_DYNPWD_FLAG_CANCEL_SHIFT)

var USER_DYNPWD_FLAG_CANCEL	=
  (1 << USER_DYNPWD_FLAG_CANCEL_SHIFT)
  
var USER_DYNPWD_EDTQ_ED_YEAR_OFFSET_MAX =	15
var USER_DYNPWD_EDTQ_TAG_VALUE=	0x2A	/* 0b101010 */

var USER_DYNPWD_EDTQ_ST_HOUR_BITS=	5
var USER_DYNPWD_EDTQ_ET_HOUR_BITS=	5
var USER_DYNPWD_EDTQ_ED_DAY_BITS=	5
var USER_DYNPWD_EDTQ_ED_MONTH_BITS=	4
var USER_DYNPWD_EDTQ_ED_YEAR_OFFSET_BITS=	4
var USER_DYNPWD_EDTQ_TAG_BITS=	6

function USER_DYNPWD_EDTQ_FIELD_MASK(FIELD)	{
	return ((1 << FIELD ) - 1)
}
var USER_DYNPWD_EDTQ_ST_HOUR_SHIFT	=
	(USER_DYNPWD_GENERAL_SHIFT + USER_DYNPWD_GENERAL_BITS)
var USER_DYNPWD_EDTQ_ET_HOUR_SHIFT	=
	(USER_DYNPWD_EDTQ_ST_HOUR_SHIFT + USER_DYNPWD_EDTQ_ST_HOUR_BITS)
var USER_DYNPWD_EDTQ_ED_DAY_SHIFT	=
	(USER_DYNPWD_EDTQ_ET_HOUR_SHIFT + USER_DYNPWD_EDTQ_ET_HOUR_BITS)
var USER_DYNPWD_EDTQ_ED_MONTH_SHIFT=
	(USER_DYNPWD_EDTQ_ED_DAY_SHIFT + USER_DYNPWD_EDTQ_ED_DAY_BITS)
var USER_DYNPWD_EDTQ_ED_YEAR_OFFSET_SHIFT	=
	(USER_DYNPWD_EDTQ_ED_MONTH_SHIFT + USER_DYNPWD_EDTQ_ED_MONTH_BITS)
var USER_DYNPWD_EDTQ_TAG_SHIFT	=
	(USER_DYNPWD_EDTQ_ED_YEAR_OFFSET_SHIFT + USER_DYNPWD_EDTQ_ED_YEAR_OFFSET_BITS)

var USER_DYNPWD_EDTQ_TOTAL_BITS=
	(USER_DYNPWD_EDTQ_TAG_SHIFT + USER_DYNPWD_EDTQ_TAG_BITS)
var USER_DYNPWD_EDTQ_TOTAL_UNITS=
	((USER_DYNPWD_EDTQ_TOTAL_BITS + 3) >> 2)

function USER_DYNPWD_EDTQ_FIELD(value, FIELD)	{
 return 	(((value) >> FIELD ) 
		& USER_DYNPWD_EDTQ_FIELD_MASK(FIELD))
	
}
var GKT_EPARAM=	-1
var GKT_DT_DAY_HOURS=	24
var GKT_DT_MINUTE_SECONDS = 60
var USER_DYNPWD_TYPE_NOTS = 0 
var USER_DYNPWD_TYPE_EDTQ = 1 ;
var USER_DYNPWD_TYPE_SDED = 2;
var USER_DYNPWD_NOTS_TIMES_MAX=	10

var USER_DYNPWD_NOTS_MINUTE_STEP=	10
var USER_DYNPWD_NOTS_MINUTE_STEPS_MAX=
	(GKT_DT_MINUTE_SECONDS / USER_DYNPWD_NOTS_MINUTE_STEP)

var USER_DYNPWD_NOTS_YEAR_OFFSET_MAX=	15
var USER_DYNPWD_NOTS_TAG_VALUE=	0x0A	/* 0b1010 */

/* NOTS: number of times */
var USER_DYNPWD_NOTS_TIMES_BITS=	4
var USER_DYNPWD_NOTS_MINUTE_STEPS_BITS=	3
var USER_DYNPWD_NOTS_HOUR_BITS=	5
var USER_DYNPWD_NOTS_DAY_BITS	=5
var USER_DYNPWD_NOTS_MONTH_BITS	=4
var USER_DYNPWD_NOTS_YEAR_OFFSET_BITS=	4
var USER_DYNPWD_NOTS_TAG_BITS=	4

function USER_DYNPWD_NOTS_FIELD_MASK(FIELD)	{
	((1 << FIELD) - 1)
}
var USER_DYNPWD_NOTS_TIMES_SHIFT	=
	(USER_DYNPWD_GENERAL_SHIFT + USER_DYNPWD_GENERAL_BITS)
var USER_DYNPWD_NOTS_MINUTE_STEPS_SHIFT	=
	(USER_DYNPWD_NOTS_TIMES_SHIFT + USER_DYNPWD_NOTS_TIMES_BITS)
var USER_DYNPWD_NOTS_HOUR_SHIFT	=
	(USER_DYNPWD_NOTS_MINUTE_STEPS_SHIFT + USER_DYNPWD_NOTS_MINUTE_STEPS_BITS)
var USER_DYNPWD_NOTS_DAY_SHIFT	=
	(USER_DYNPWD_NOTS_HOUR_SHIFT + USER_DYNPWD_NOTS_HOUR_BITS)
var USER_DYNPWD_NOTS_MONTH_SHIFT	=
	(USER_DYNPWD_NOTS_DAY_SHIFT + USER_DYNPWD_NOTS_DAY_BITS)
var USER_DYNPWD_NOTS_YEAR_OFFSET_SHIFT	=
	(USER_DYNPWD_NOTS_MONTH_SHIFT + USER_DYNPWD_NOTS_MONTH_BITS)
var USER_DYNPWD_NOTS_TAG_SHIFT	=
	(USER_DYNPWD_NOTS_YEAR_OFFSET_SHIFT + USER_DYNPWD_NOTS_YEAR_OFFSET_BITS)

var USER_DYNPWD_NOTS_TOTAL_BITS	=
	(USER_DYNPWD_NOTS_TAG_SHIFT + USER_DYNPWD_NOTS_TAG_BITS)
var USER_DYNPWD_NOTS_TOTAL_UNITS=
	((USER_DYNPWD_NOTS_TOTAL_BITS + 3) >> 2)
	
function USER_DYNPWD_NOTS_FIELD(value, FIELD)	{
 return	(((value) >> FIELD ) 
		& USER_DYNPWD_NOTS_FIELD_MASK(FIELD))
  }


  var USER_DYNPWD_SDED_SD_YEAR_OFFSET_MAX	=15
  var USER_DYNPWD_SDED_ED_YEAR_OFFSET_MAX	=5
  var USER_DYNPWD_SDED_TAG_VALUE=	0x0A	/* 0b1010 */
  
  var USER_DYNPWD_SDED_SD_DAY_BITS=	5
  var USER_DYNPWD_SDED_SD_MONTH_BITS=	4
  var USER_DYNPWD_SDED_SD_YEAR_OFFSET_BITS=	4
  var USER_DYNPWD_SDED_ED_DAY_BITS=	5
  var USER_DYNPWD_SDED_ED_MONTH_BITS=	4
  var USER_DYNPWD_SDED_ED_YEAR_OFFSET_BITS=	3
  var USER_DYNPWD_SDED_TAG_BITS=	4
  
  function USER_DYNPWD_SDED_FIELD_MASK(FIELD)	{
    ((1 << FIELD) - 1)
  }
  var USER_DYNPWD_SDED_SD_DAY_SHIFT	=
    (USER_DYNPWD_GENERAL_SHIFT + USER_DYNPWD_GENERAL_BITS)
  var USER_DYNPWD_SDED_SD_MONTH_SHIFT	=
    (USER_DYNPWD_SDED_SD_DAY_SHIFT + USER_DYNPWD_SDED_SD_DAY_BITS)
  var USER_DYNPWD_SDED_SD_YEAR_OFFSET_SHIFT=
    (USER_DYNPWD_SDED_SD_MONTH_SHIFT + USER_DYNPWD_SDED_SD_MONTH_BITS)
  var USER_DYNPWD_SDED_ED_DAY_SHIFT	=
    (USER_DYNPWD_SDED_SD_YEAR_OFFSET_SHIFT + USER_DYNPWD_SDED_SD_YEAR_OFFSET_BITS)
  var USER_DYNPWD_SDED_ED_MONTH_SHIFT	=
    (USER_DYNPWD_SDED_ED_DAY_SHIFT + USER_DYNPWD_SDED_ED_DAY_BITS)
  var USER_DYNPWD_SDED_ED_YEAR_OFFSET_SHIFT=
    (USER_DYNPWD_SDED_ED_MONTH_SHIFT + USER_DYNPWD_SDED_ED_MONTH_BITS)
  var USER_DYNPWD_SDED_TAG_SHIFT	=
    (USER_DYNPWD_SDED_ED_YEAR_OFFSET_SHIFT + USER_DYNPWD_SDED_ED_YEAR_OFFSET_BITS)
  
  var USER_DYNPWD_SDED_TOTAL_BITS	=
    (USER_DYNPWD_SDED_TAG_SHIFT + USER_DYNPWD_SDED_TAG_BITS)
  var USER_DYNPWD_SDED_TOTAL_UNITS	=
    ((USER_DYNPWD_SDED_TOTAL_BITS + 3) >> 2)
  
  function USER_DYNPWD_SDED_FIELD(value, FIELD)	{
    (((value) >> FIELD) 
      & USER_DYNPWD_SDED_FIELD_MASK(FIELD))
    }


function user_dynpwd_get_check_value(units, units_size) {
  var check_value, i;
  check_value = 0;
  for (i = 0; i < units_size; i++)
    check_value += units[i] ^ (i + 1);
  while (check_value > 0x0f)
    check_value = (check_value >> 4) ^ (check_value & 0x0f);
  // console.log(" 6 :  %d \n", check_value);
  return check_value;
}

/* 0 <= shift < 0x10 */
function user_dynpwd_rotate_left_shift(units, units_size, shift) {
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



function user_dynpwd_rotate_right_shift(units,
  units_size, shift) {
  var shift_units_nums, shift_bits;
  var i, offset, mask;
  var tmp = new Array(4);

  if (!shift)
    shift = 0x10;

  shift_units_nums = (shift >> 2) % units_size;
  if (shift_units_nums) {
    offset = units_size - shift_units_nums;
    for (i = 0; i < shift_units_nums; i++)
      tmp[i] = units[offset + i];
    while (offset-- > 0)
      units[offset + shift_units_nums] = units[offset];
    while (shift_units_nums--)
      units[shift_units_nums] = tmp[shift_units_nums];
  }

  shift_bits = shift & 3;
  if (shift_bits) {
    mask = (1 << shift_bits) - 1;
    tmp[0] = (units[units_size - 1] & mask) << (4 - shift_bits);
    for (i = units_size - 1; i > 0; i--) {
      units[i] >>= shift_bits;
      units[i] |= (units[i - 1] & mask) << (4 - shift_bits);
    }
    units[0] = tmp[0] | (units[0] >> shift_bits);
  }
}



function user_dynpwd_xor(data_units,
  data_units_size, xor_value) {
  var i;

  if (xor_value != 0) {
    for (i = 0; i < data_units_size; i++)
      data_units[i] ^= xor_value;
  } else {
    for (i = 0; i < data_units_size; i++)
      data_units[i] ^= (i & 0x0f);
  }
}


function user_dynpwd_build_key_units(key_units,
  key_string, key_length) {
  var key_value = Long.fromString( "0", true).toNumber() ;
  var key_units_size, i;
  var ch_value, ch_value_prev, unit_value;
  var arith_seq, delta_base, delta;
  var key_ch;

  i = 0;
  key_value = key_length;
  arith_seq = 1;
  while ((i < key_length) &&
    ((key_ch = key_string[i]) != '\0')) {
    ch_value = key_ch - '0';
    key_value = (key_value * 10) + ch_value;

    if (arith_seq) {
      if (i > 2) {
        delta = ch_value - ch_value_prev;
        if (delta != delta_base)
          arith_seq = 0;
      } else if (1 == i)
        delta_base = ch_value - ch_value_prev;
      ch_value_prev = ch_value;
    }

    i++;
  }

  key_units_size = 0;
  do {
    key_units[key_units_size++] = (key_value & 0x0f);
    key_value = Long.fromString(key_value + "", true).shiftRight(4).toNumber() ;;
  } while (key_value && (key_units_size < USER_DYNPWD_KEY_UNITS_MAXSIZE));

  if (arith_seq) {
    key_units[key_units_size++] = key_string[0] - '0' + 3;
    if (delta_base < 10)
      key_units[key_units_size++] = delta_base;
    else
      key_units[key_units_size++] = (0 - delta_base) & 0x0f;
  }

  return key_units_size;
}


function user_dynpwd_encrypt(data_units,
  data_units_size, key_string,
  key_length) {
  var key_units = new Uint8Array(USER_DYNPWD_KEY_UNITS_MAXSIZE);
  var key_units_size, i;
  var check_value;

  key_units_size = user_dynpwd_build_key_units(key_units,
    key_string, key_length);
  for (i = 0; i < key_units_size; i++) {
    user_dynpwd_xor(data_units, data_units_size,

      key_units[i]);
    user_dynpwd_rotate_left_shift(data_units,
      data_units_size, key_units[i]);
  }

  check_value = user_dynpwd_get_check_value(key_units,
    key_units_size);
  data_units[data_units_size] = check_value;

  return data_units_size + 1;
}


function user_dynpwd_decrypt(data_units,
  data_units_size, key_string,
  key_length) {
  var key_units = new Uint8Array(USER_DYNPWD_KEY_UNITS_MAXSIZE);
  var key_units_size, _data_units_size, i;
  var check_value;

  key_units_size = user_dynpwd_build_key_units(key_units,
    key_string, key_length);

  check_value = user_dynpwd_get_check_value(key_units,
    key_units_size);
  _data_units_size = data_units_size - 1;
  if (data_units[_data_units_size] != check_value) {
    console.log("user_dynpwd: decrypt check_value unmatch - (%x)(%x)\n",
      check_value, data_units[_data_units_size]);

    return 0;
  }

  for (i = key_units_size; i-- > 0;) {
    user_dynpwd_rotate_right_shift(data_units,

      _data_units_size, key_units[i]);
    user_dynpwd_xor(data_units, _data_units_size,
      key_units[i]);
  }

  return _data_units_size;
}


function user_dynpwd_confuse(data_units,
  data_units_size) {
  var check_value;

  check_value = user_dynpwd_get_check_value(data_units,
    data_units_size);
  user_dynpwd_xor(data_units, data_units_size, check_value);
  user_dynpwd_rotate_left_shift(data_units,
    data_units_size, check_value);
  data_units[data_units_size] = check_value;

  return (data_units_size + 1);
}


function user_dynpwd_unconfuse(data_units,
  data_units_size) {
  var check_value, check_value_calc;

  check_value = data_units[data_units_size - 1];
  user_dynpwd_rotate_right_shift(data_units,
    data_units_size - 1, check_value);
  user_dynpwd_xor(data_units, data_units_size - 1,
    check_value);

  check_value_calc = user_dynpwd_get_check_value(data_units,
    data_units_size - 1);
  if (check_value != check_value_calc) {
    console.log("user_dynpwd: unconfuse failed - %x @ %x\n",
      check_value.check_value_calc);
    return 0;
  }

  return (data_units_size - 1);
}


function user_dynpwd_units_2_digits(data_units,
  data_units_size, digits) {
  var digits_len, units_offset, units_len_left;
  var value_0, value_1, value_2;

  units_offset = 0;
  digits_len = 0;
  while ((units_offset + 4) <= data_units_size) {
    value_0 = (data_units[units_offset] << 4) | data_units[units_offset + 1];
    value_2 = (data_units[units_offset + 2] << 4) | data_units[units_offset + 3];

    value_1 = (parseInt(value_0 / 100) * 3) + parseInt(value_2 / 100);
    if (!units_offset && !value_1)
      value_1 = 9;
    value_0 %= 100;
    value_2 %= 100;

    // digits[digits_len++] = '0' + value_1;
    // digits[digits_len++] = '0' + parseInt(value_0 / 10);
    // digits[digits_len++] = '0' + parseInt(value_0 % 10);
    // digits[digits_len++] = '0' + parseInt(value_2 / 10);
    // digits[digits_len++] = '0' + parseInt(value_2 % 10);
    digits[digits_len++] =  value_1;
    digits[digits_len++] =  parseInt(value_0 / 10);
    digits[digits_len++] =  parseInt(value_0 % 10);
    digits[digits_len++] =  parseInt(value_2 / 10);
    digits[digits_len++] =  parseInt(value_2 % 10);

    units_offset += 4;
  }

  units_len_left = data_units_size - units_offset;
  if (3 == units_len_left) {
    value_0 = (data_units[units_offset] << 8) |
      (data_units[units_offset + 1] << 4) |
      data_units[units_offset + 2];
      // digits[digits_len++] = '0' + parseInt((value_0 % 100) / 10);
      // digits[digits_len++] = '0' + parseInt(value_0 / 1000);
      // digits[digits_len++] = '0' + (parseInt(value_0 / 100) % 10);
      // digits[digits_len++] = '0' + parseInt(value_0 % 10);
      digits[digits_len++] = parseInt((value_0 % 100) / 10);
      digits[digits_len++] =parseInt(value_0 / 1000);
      digits[digits_len++] =(parseInt(value_0 / 100) % 10);
      digits[digits_len++] = parseInt(value_0 % 10);
  } else if (2 == units_len_left) {
    value_0 = (data_units[units_offset] << 4) | data_units[units_offset + 1];
    // digits[digits_len++] = '0' + parseInt((value_0 % 100) / 10);
    // digits[digits_len++] = '0' + parseInt(value_0 / 100);
    // digits[digits_len++] = '0' + parseInt(value_0 % 10);
    digits[digits_len++] = parseInt((value_0 % 100) / 10);
    digits[digits_len++] =  parseInt(value_0 / 100);
    digits[digits_len++] =  parseInt(value_0 % 10);
  } else if (1 == units_len_left) {
    value_0 = data_units[units_offset];
    // digits[digits_len++] = '0' + parseInt(value_0 / 10);
    // digits[digits_len++] = '0' + parseInt(value_0 % 10);
    digits[digits_len++] = parseInt(value_0 / 10);
    digits[digits_len++] = parseInt(value_0 % 10);
  }

//  digits[digits_len] = '\0';

  return digits_len;
}


function user_dynpwd_digits_2_units(digits,
  digits_len, data_units) {
  var data_units_size, digits_offset, digits_len_left;
  var value_0, value_1, value_2;

  digits_offset = 0;
  data_units_size = 0;
  while ((digits_offset + 5) <= digits_len) {
    value_1 = (digits[digits_offset] - '0');
    value_0 = ((digits[digits_offset + 1] - '0') * 10) +
      (digits[digits_offset + 2] - '0');
    value_2 = ((digits[digits_offset + 3] - '0') * 10) +
      (digits[digits_offset + 4] - '0');

    if (!digits_offset && (9 == value_1))
      value_1 = 0;
    value_0 += parseInt(value_1 / 3) * 100;
    value_2 += parseInt(value_1 % 3) * 100;

    data_units[data_units_size++] = value_0 >> 4;
    data_units[data_units_size++] = value_0 & 0x0f;
    data_units[data_units_size++] = value_2 >> 4;
    data_units[data_units_size++] = value_2 & 0x0f;

    digits_offset += 5;
  }

  digits_len_left = digits_len - digits_offset;
  if (4 == digits_len_left) {
    value_0 = ((digits[digits_offset] - '0') * 10) +
      ((digits[digits_offset + 1] - '0') * 1000) +
      ((digits[digits_offset + 2] - '0') * 100) +
      ((digits[digits_offset + 3] - '0'));
    data_units[data_units_size++] = (value_0 >> 8) & 0x0f;
    data_units[data_units_size++] = (value_0 >> 4) & 0x0f;
    data_units[data_units_size++] = value_0 & 0x0f;
  } else if (3 == digits_len_left) {
    value_0 = ((digits[digits_offset] - '0') * 10) +
      ((digits[digits_offset + 1] - '0') * 100) +
      ((digits[digits_offset + 2] - '0'));
    data_units[data_units_size++] = (value_0 >> 4) & 0x0f;
    data_units[data_units_size++] = value_0 & 0x0f;
  } else if (2 == digits_len_left) {
    value_0 = ((digits[digits_offset] - '0') * 10) +
      ((digits[digits_offset + 1] - '0'));
    data_units[data_units_size++] = value_0 & 0x0f;
  } else {
    gkt_error("user_dynpwd_d2u: digits_len(%u) invalid\n", digits_len);
    data_units_size = 0;
  }

  return data_units_size;
}


function user_dynpwd_generate_digits(key_string,
  key_length, data_units,
  data_units_size, digits) {
  var _data_units_size, digits_size;

  if (!key_string || !key_length || (key_length > USER_DYNPWD_KEY_DIGITS_MAXSIZE) ||
    !data_units || !data_units_size ||
    !digits) {
    gkt_error("user_dynpwd_generate: params invalid!\n");
    return 0;
  }

  /* confuse */
  _data_units_size = user_dynpwd_confuse(data_units,
    data_units_size);
    // console.log(1 );
    // console.log( data_units );
  /* encrypt */
  _data_units_size = user_dynpwd_encrypt(data_units,
    _data_units_size, key_string, key_length);

    // console.log( 2 );
    // console.log( data_units );
  /* units to digits */
  digits_size = user_dynpwd_units_2_digits(data_units,
    _data_units_size, digits);
    // console.log( 3 );
    // console.log( digits );
  return digits_size;
}


function user_dynpwd_generate_values(key_string,
  key_length, digits,
  digits_size, values) {
  var data_units = new Uint8Array(USER_DYNPWD_DATA_UNITS_MAXSIZE);
  var data_units_size;

  var i, dw_index, offset;

  if (!key_string || !key_length || (key_length > USER_DYNPWD_KEY_DIGITS_MAXSIZE) ||
    !digits || !digits_size || (digits_size > USER_DYNPWD_DIGITS_MAXSIZE) ||
    !values) {
    gkt_error("user_dynpwd: params invalid!\n");
    return 0;
  }

  /* digits to units */
  data_units_size = user_dynpwd_digits_2_units(digits,
    digits_size, data_units);

  /* decrypt */
  data_units_size = user_dynpwd_decrypt(data_units,
    data_units_size, key_string, key_length);
  if (!data_units_size)
    return 0;

  data_units_size = user_dynpwd_unconfuse(data_units,
    data_units_size);
  if (!data_units_size)
    return 0;

  for (i = 0; i < data_units_size; i++) {
    dw_index = i >> 3;
    offset = i & 7;
    if (!offset)
      values[dw_index] = 0;
    values[dw_index] |= data_units[i] << (offset * 4);
  }

  return ((data_units_size + 7) >> 3);
}


/**
 * 生成次数密码的密钥
 * @param {} units 
 * @param {*} param 
 * @param {*} cancelled 
 */
function user_dynpwd_nots_build_units(units,
  param, cancelled) {
  var year_offset, minute_steps, value = new Int32Array(1)[0];
  var i;

  if ((param.nbr_of_times > 0) &&
    (param.nbr_of_times <= USER_DYNPWD_NOTS_TIMES_MAX) &&
    (param.date_time.date.year >= USER_DYNPWD_BASE_YEAR) ) {
    year_offset = param.date_time.date.year - USER_DYNPWD_BASE_YEAR;
    if (year_offset >= USER_DYNPWD_NOTS_YEAR_OFFSET_MAX) {
      // console.log("user_dynpwd_nots: year(%u) invalid!\n",
        // param.date_time.date.year);
      return GKT_EPARAM;
    }

    value = USER_DYNPWD_TYPE_NOTS;
    if (cancelled)
      value |= USER_DYNPWD_FLAG_CANCEL;

    value |= param.nbr_of_times << USER_DYNPWD_NOTS_TIMES_SHIFT;

    minute_steps = parseInt(param.date_time.time.minute / USER_DYNPWD_NOTS_MINUTE_STEP);
    value |= minute_steps << USER_DYNPWD_NOTS_MINUTE_STEPS_SHIFT;
    value |= param.date_time.time.hour << USER_DYNPWD_NOTS_HOUR_SHIFT;
    value |= param.date_time.date.day << USER_DYNPWD_NOTS_DAY_SHIFT;
    value |= param.date_time.date.month << USER_DYNPWD_NOTS_MONTH_SHIFT;
    value |= year_offset << USER_DYNPWD_NOTS_YEAR_OFFSET_SHIFT;
    value |= USER_DYNPWD_NOTS_TAG_VALUE << USER_DYNPWD_NOTS_TAG_SHIFT;

    // console.log("user_dynpwd_nots: value = %d \n", value);
    for (i = 0; i < USER_DYNPWD_NOTS_TOTAL_UNITS; i++) {
      units[i] = value & 0x0f;
      value >>= 4;
    }

    return USER_DYNPWD_NOTS_TOTAL_UNITS;
  }

  return GKT_EPARAM;
}
/**
 * 取消次数密码的密钥
 * @param {} units 
 * @param {*} param 
 * @param {*} cancelled 
 */
function user_dynpwd_nots_retrieve(values,
  param, cancelled) {
  var year_offset, minute_steps;
  var tag;

  cancelled = (values[0] & USER_DYNPWD_FLAG_CANCEL_MASK) ? 1 : 0;

  param.nbr_of_times = USER_DYNPWD_NOTS_FIELD(values[0], TIMES);
  if (!param.nbr_of_times || (param.nbr_of_times > USER_DYNPWD_NOTS_TIMES_MAX)) {
    console.log("user_dynpwd_nots: nbr_of_times(%u) invalid!\n", param.nbr_of_times);
    return GKT_EPARAM;
  }

  param.date_time.time.reserved = 0;
  param.date_time.time.second = 0;

  minute_steps = USER_DYNPWD_NOTS_FIELD(values[0], MINUTE_STEPS);
  if (minute_steps >= USER_DYNPWD_NOTS_MINUTE_STEPS_MAX) {
    console.log("user_dynpwd_nots: minute_steps(%u) invalid!\n", minute_steps);
    return GKT_EPARAM;
  }
  param.date_time.time.minute = minute_steps * USER_DYNPWD_NOTS_MINUTE_STEP;

  param.date_time.time.hour = USER_DYNPWD_NOTS_FIELD(values[0], HOUR);
  if (param.date_time.time.hour >= GKT_DT_DAY_HOURS) {
    console.log("user_dynpwd_nots: hour(%u) invalid!\n", param.date_time.time.hour);
    return GKT_EPARAM;
  }

  year_offset = USER_DYNPWD_NOTS_FIELD(values[0], YEAR_OFFSET);
  if (year_offset >= USER_DYNPWD_NOTS_YEAR_OFFSET_MAX) {
    console.log("user_dynpwd_nots: year_offset(%u) invalid!\n", year_offset);
    return GKT_EPARAM;
  }

  param.date_time.date.day = USER_DYNPWD_NOTS_FIELD(values[0], DAY);
  param.date_time.date.month = USER_DYNPWD_NOTS_FIELD(values[0], MONTH);
  param.date_time.date.year = year_offset + USER_DYNPWD_BASE_YEAR;


  tag = USER_DYNPWD_NOTS_FIELD(values[0], TAG);
  if (tag != USER_DYNPWD_NOTS_TAG_VALUE) {
    console.log("user_dynpwd_nots: tag(%u) invalid!\n", tag);
    return GKT_EPARAM;
  }

  return GKT_SUCCESS;
}


/**
 * 生成时间段密码的密钥
 * @param {*} units 
 * @param {*} param 
 * @param {*} cancelled 
 */
function user_dynpwd_edtq_build_units(units,
  param, cancelled) {
  var year_offset;
  var value;
  var i;

  if ((param.st_hour < param.et_hour) &&
    (param.st_hour < GKT_DT_DAY_HOURS) &&
    (param.et_hour <= GKT_DT_DAY_HOURS) &&
    (param.expire_date.year >= USER_DYNPWD_BASE_YEAR) ) {
    console.log("user_dynpwd_edtq: (%d) SH(%u) EH(%u) ED(%04u-%02u-%02u)\n",
      cancelled, param.st_hour, param.et_hour,
      param.expire_date.year, param.expire_date.month,
      param.expire_date.day);

    year_offset = param.expire_date.year - USER_DYNPWD_BASE_YEAR;
    if (year_offset >= USER_DYNPWD_EDTQ_ED_YEAR_OFFSET_MAX) {
      gkt_error("user_dynpwd_edtq: expire year(%u) invalid!\n",
        param.expire_date.year);
      return GKT_EPARAM;
    }

    value = USER_DYNPWD_TYPE_EDTQ;
    if (cancelled)
      value |= USER_DYNPWD_FLAG_CANCEL;

    value |= param.st_hour << USER_DYNPWD_EDTQ_ST_HOUR_SHIFT;
    value |= param.et_hour << USER_DYNPWD_EDTQ_ET_HOUR_SHIFT;
    value |= param.expire_date.day << USER_DYNPWD_EDTQ_ED_DAY_SHIFT;
    value |= param.expire_date.month << USER_DYNPWD_EDTQ_ED_MONTH_SHIFT;
    value |= year_offset << USER_DYNPWD_EDTQ_ED_YEAR_OFFSET_SHIFT;
    value |= USER_DYNPWD_EDTQ_TAG_VALUE << USER_DYNPWD_EDTQ_TAG_SHIFT;

    console.log("user_dynpwd_edtq: value = 0x%08x\n", value);
    for (i = 0; i < USER_DYNPWD_EDTQ_TOTAL_UNITS; i++) {
      units[i] = value & 0x0f;
      value >>= 4;
    }

    return USER_DYNPWD_EDTQ_TOTAL_UNITS;
  }

  return GKT_EPARAM;
}

/**
 * 取消时间段密码的密钥
 * @param {*} units 
 * @param {*} param 
 * @param {*} cancelled 
 */
function user_dynpwd_edtq_retrieve(values,
  param, cancelled) {
  var year_offset, tag;

  cancelled = (values[0] & USER_DYNPWD_FLAG_CANCEL_MASK) ? 1 : 0;

  param.st_hour = USER_DYNPWD_EDTQ_FIELD(values[0], ST_HOUR);
  if (param.st_hour >= GKT_DT_DAY_HOURS) {
    console.log("user_dynpwd_edtq: start hour(%u) invalid!\n",
      param.st_hour);
    return GKT_EPARAM;
  }

  param.et_hour = USER_DYNPWD_EDTQ_FIELD(values[0], ET_HOUR);
  if ((param.et_hour > GKT_DT_DAY_HOURS) ||
    (param.et_hour <= param.st_hour)) {
    console.log("user_dynpwd_edtq: start hour(%u) invalid!\n",
      param.et_hour);
    return GKT_EPARAM;
  }

  year_offset = USER_DYNPWD_EDTQ_FIELD(values[0], ED_YEAR_OFFSET);
  if (year_offset >= USER_DYNPWD_EDTQ_ED_YEAR_OFFSET_MAX) {
    console.log("user_dynpwd_edtq: year_offset(%u) invalid!\n",
      year_offset);
    return GKT_EPARAM;
  }

  param.expire_date.day = USER_DYNPWD_EDTQ_FIELD(values[0], ED_DAY);
  param.expire_date.month = USER_DYNPWD_EDTQ_FIELD(values[0], ED_MONTH);
  param.expire_date.year = year_offset + USER_DYNPWD_BASE_YEAR;


  tag = USER_DYNPWD_EDTQ_FIELD(values[0], TAG);
  if (tag != USER_DYNPWD_EDTQ_TAG_VALUE) {
    console.log("user_dynpwd_edtq: tag(%u) invalid!\n", tag);
    return GKT_EPARAM;
  }

  return GKT_SUCCESS;
}

/**
 * 生成日期段密码的密钥
 * @param {*} units 
 * @param {*} param 
 * @param {*} cancelled 
 */
function user_dynpwd_sded_build_units(units,
  param, cancelled) {
  var year_offset;
  var value;
  var i;

  if ((param.start_date.year >= USER_DYNPWD_BASE_YEAR) &&
    (param.expire_date.year >= USER_DYNPWD_BASE_YEAR) ) {
    console.log("user_dynpwd_sded: (%d) SD(%04u-%02u-%02u) ED(%04u-%02u-%02u)\n",
      cancelled, param.start_date.year, param.start_date.month,
      param.start_date.day, param.expire_date.year,
      param.expire_date.month, param.expire_date.day);


    value = USER_DYNPWD_TYPE_SDED;
    if (cancelled)
      value |= USER_DYNPWD_FLAG_CANCEL;

    year_offset = param.start_date.year - USER_DYNPWD_BASE_YEAR;
    if (year_offset >= USER_DYNPWD_SDED_SD_YEAR_OFFSET_MAX) {
      gkt_error("user_dynpwd_sded: start year(%u) invalid!\n",
        param.start_date.year);
      return GKT_EPARAM;
    }
    value |= param.start_date.day << USER_DYNPWD_SDED_SD_DAY_SHIFT;
    value |= param.start_date.month << USER_DYNPWD_SDED_SD_MONTH_SHIFT;
    value |= year_offset << USER_DYNPWD_SDED_SD_YEAR_OFFSET_SHIFT;

    year_offset = param.expire_date.year - param.start_date.year;
    if (year_offset >= USER_DYNPWD_SDED_ED_YEAR_OFFSET_MAX) {
      gkt_error("user_dynpwd_sded: expire year(%u) invalid!\n", param.expire_date.year);
      return GKT_EPARAM;
    }
    value |= param.expire_date.day << USER_DYNPWD_SDED_ED_DAY_SHIFT;
    value |= param.expire_date.month << USER_DYNPWD_SDED_ED_MONTH_SHIFT;
    value |= year_offset << USER_DYNPWD_SDED_ED_YEAR_OFFSET_SHIFT;

    value |= USER_DYNPWD_SDED_TAG_VALUE << USER_DYNPWD_SDED_TAG_SHIFT;

    console.log("user_dynpwd_sded: value = 0x%08x\n", value);
    for (i = 0; i < USER_DYNPWD_SDED_TOTAL_UNITS; i++) {
      units[i] = value & 0x0f;
      value >>= 4;
    }

    return USER_DYNPWD_SDED_TOTAL_UNITS;
  }

  return GKT_EPARAM;
}

/**
 * 取消日期段密码的密钥
 * @param {*} units 
 * @param {*} param 
 * @param {*} cancelled 
 */
function user_dynpwd_sded_retrieve(values,
  param, cancelled) {
  var year_offset, tag;

  cancelled = (values[0] & USER_DYNPWD_FLAG_CANCEL_MASK) ? 1 : 0;

  year_offset = USER_DYNPWD_SDED_FIELD(values[0], SD_YEAR_OFFSET);
  if (year_offset >= USER_DYNPWD_SDED_SD_YEAR_OFFSET_MAX) {
    console.log("user_dynpwd_sded: start year_offset(%u) invalid!\n",
      year_offset);
    return GKT_EPARAM;
  }

  param.start_date.day = USER_DYNPWD_SDED_FIELD(values[0], SD_DAY);
  param.start_date.month = USER_DYNPWD_SDED_FIELD(values[0], SD_MONTH);
  param.start_date.year = year_offset + USER_DYNPWD_BASE_YEAR;

  year_offset = USER_DYNPWD_SDED_FIELD(values[0], ED_YEAR_OFFSET);
  if (year_offset >= USER_DYNPWD_SDED_ED_YEAR_OFFSET_MAX) {
    console.log("user_dynpwd_sded: expire year_offset(%u) invalid!\n",
      year_offset);
    return GKT_EPARAM;
  }

  param.expire_date.day = USER_DYNPWD_SDED_FIELD(values[0], ED_DAY);
  param.expire_date.month = USER_DYNPWD_SDED_FIELD(values[0], ED_MONTH);
  param.expire_date.year = year_offset + param.start_date.year;
if (param.expire_date.u32_value < param.start_date.u32_value) {
    console.log("user_dynpwd_sded: expire_date not later than start_date!\n");
    return GKT_EPARAM;
  }

  tag = USER_DYNPWD_SDED_FIELD(values[0], TAG);
  if (tag != USER_DYNPWD_SDED_TAG_VALUE) {
    console.log("user_dynpwd_sded: tag(%u) invalid!\n", tag);
    return GKT_EPARAM;
  }

  return GKT_SUCCESS;
}
function arrayToString(array){
  var str = '';
  for(var i = 0 ; i < array.length; i++){
    str += array[i];
  }
  return str;
}

function generateSDEDPWD(key, sded_param,  cancelled){
  
  var data_units = new Array();
  var digits = new Array();
  
  var data_units_size = user_dynpwd_sded_build_units(data_units, 
    sded_param, cancelled);
console.log(data_units);
	var	digits_size = user_dynpwd_generate_digits(key, key.length ,
              data_units, data_units_size, digits);
  // var values_size = user_dynpwd_generate_values(de_key, 
  //               strlen(de_key), digits, digits_size, values);            


  console.log("generateSDEDPWD: <%d> digits(%s)\n", cancelled, digits);
  console.log(digits);
  return arrayToString(digits);
}


function generateNOTSPWD(key, nots_param,  cancelled){
  
  var data_units = new Array();
  var digits = new Array();
  
  var data_units_size = user_dynpwd_nots_build_units(data_units, 
    nots_param, cancelled);
  
	var	digits_size = user_dynpwd_generate_digits(key, key.length ,
              data_units, data_units_size, digits);
  // var values_size = user_dynpwd_generate_values(de_key, 
  //               strlen(de_key), digits, digits_size, values);            


  console.log("generateNOTSPWD: <%d> digits(%s)\n", cancelled, digits);
  console.log(digits);
  return arrayToString(digits);
}

function generateEDTQPWD(key, edtq_param,  cancelled){
  
  var data_units = new Array();
  var digits = new Array();
  
  var data_units_size = user_dynpwd_edtq_build_units(data_units, 
    edtq_param, cancelled);
  
	var	digits_size = user_dynpwd_generate_digits(key, key.length ,
              data_units, data_units_size, digits);
  // var values_size = user_dynpwd_generate_values(de_key, 
  //               strlen(de_key), digits, digits_size, values);            


  console.log("generateEDTQPWD: <%d> digits(%s)\n", cancelled, digits);
  console.log(digits);
  return arrayToString(digits);
}
 
module.exports.generateSDEDPWD = generateSDEDPWD;
module.exports.generateEDTQPWD = generateEDTQPWD;
module.exports.generateNOTSPWD = generateNOTSPWD;