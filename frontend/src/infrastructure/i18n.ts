import _ from "lodash"
import i18n from "i18next";
import {initReactI18next} from "react-i18next";
import LanguageDetector from 'i18next-browser-languagedetector';
import {setLocaleObject, yupI18nNs, yupResources} from "../yup/localization";
import {beeResources} from "./localization";

i18n
  .use(initReactI18next)
  .use(LanguageDetector)
  .init({
    resources: _.merge(yupResources, beeResources),
    fallbackLng: ["en", "zh"],
    interpolation: {
      escapeValue: false
    },
  })
  .then((t) => {
    setLocaleObject(key => t(key, yupI18nNs))
  });

i18n.on("languageChanged", function (lng) {
  setLocaleObject(key => i18n.t(key, yupI18nNs))
})

export default i18n;
