import en from "./en.json";
import zh from "./zh.json";
import printValue from "yup/es/util/printValue";
import {LocaleObject} from "yup/es/locale";
import {setLocale} from "yup";

export const yupResources = {
  ...en,
  ...zh
};

export const yupNs = "yup";
export const yupI18nNs = {ns: yupNs};

export function getLocaleObject(t: ((key: string) => string) = key => key): LocaleObject {
  return {
    mixed: {
      default: t("mixed.default"),
      required: t("mixed.required"),
      oneOf: t("mixed.oneOf"),
      notOneOf: t("mixed.notOneOf"),
      notType: ({path, type, value, originalValue}) => {
        let isCast = originalValue != null && originalValue !== value;
        let msg = `${path} must be a \`${type}\` type, but the final value was: \`${printValue(value, true)}\`` + (isCast ? ` (cast from the value \`${printValue(originalValue, true)}\`).` : '.');
        if (value === null) {
          msg += `\n If "null" is intended as an empty value be sure to mark the schema as \`.nullable()\``;
        }
        return msg;
      },
      defined: t("mixed.defined"),
    },
    string: {
      length: t("string.length"),
      min: t("string.min"),
      max: t("string.max"),
      matches: t("string.matches"),
      email: t("string.email"),
      url: t("string.url"),
      uuid: t("string.uuid"),
      trim: t("string.trim"),
      lowercase: t("string.lowercase"),
      uppercase: t("string.uppercase"),
    },
    number: {
      min: t("number.min"),
      max: t("number.max"),
      lessThan: t("number.lessThan"),
      moreThan: t("number.moreThan"),
      positive: t("number.positive"),
      negative: t("number.negative"),
      integer: t("number.integer"),
    },
    date: {
      min: t("date.min"),
      max: t("date.max"),
    },
    boolean: {
      isValue: t("boolean.isValue"),
    },
    object: {
      noUnknown: t("object.noUnknown"),
    },
    array: {
      min: t("array.min"),
      max: t("array.max"),
      length: t("array.length"),
    }
  };
}

export function setLocaleObject(t: (key: string) => string): void {
  setLocale(getLocaleObject(t));
}
