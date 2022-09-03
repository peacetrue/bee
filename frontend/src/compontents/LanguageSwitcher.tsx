import {MenuItem, Select, SxProps, Theme} from "@mui/material";
import React, {useState} from "react";
import {SelectChangeEvent} from "@mui/material/Select/SelectInput";

/** 语言切换器 */
export default function LanguageSwitcher({sx, value, onChange}: LanguageSwitcherProps) {
  const [lang, setLang] = useState(value);
  return (
    <Select sx={sx} size={"small"} value={lang} onChange={e => {
      let newValue = e.target.value;
      onChange && onChange(e, newValue);
      setLang(newValue);
    }}>
      <MenuItem value="zh">中文</MenuItem>
      <MenuItem value="en">English</MenuItem>
    </Select>
  );
}

export interface LanguageSwitcherProps {
  sx?: SxProps<Theme>;
  value: string;
  onChange?: (event: SelectChangeEvent, newValue: string) => void;
}
