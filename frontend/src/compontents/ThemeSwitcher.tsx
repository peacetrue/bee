

import {IconButton, SxProps, Theme} from "@mui/material";
import Brightness4Icon from '@mui/icons-material/Brightness4';
import Brightness7Icon from '@mui/icons-material/Brightness7';
import React, {useState} from "react";

/** 主题 Theme 切换器 */
export default function ThemeSwitcher(props: ThemeSwitcherProps) {
  const {
    icons = [<Brightness7Icon/>, <Brightness4Icon/>],
    value = 0,
    onChange,
    sx
  } = props;
  const [index, setIndex] = useState(value);
  return (
    <IconButton sx={sx} onClick={(e) => {
      let newIndex = index === icons.length - 1 ? 0 : index + 1;
      onChange && onChange(e, newIndex);
      setIndex(newIndex);
    }}>
      {icons[index]}
    </IconButton>
  );
}

export interface ThemeSwitcherProps {
  icons?: React.ReactNode[];
  value?: number;
  onChange?: (event: React.SyntheticEvent, value: number) => void;
  sx?: SxProps<Theme>;
}
