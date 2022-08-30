import React from "react";
import {Theme, ThemeProvider} from "@mui/material";
import {createTheme} from "@mui/material/styles";

/** 可切换的主题 Theme 提供者 */
export default function SwitchableThemeProvider(props: SwitchableThemeProviderProps) {
  const {
    themes = SwitchableThemeProvider.defaultProps.themes,
    value = 0,
    children
  } = props;
  return (
    <ThemeProvider theme={themes[value]}>
      {children}
    </ThemeProvider>
  )
}

export interface SwitchableThemeProviderProps {
  themes?: Theme[];
  value?: number;
  children?: React.ReactNode
}

SwitchableThemeProvider.defaultProps = {
  themes: ["light", "dark"].map((value) => createTheme({palette: {mode: value as any}}))
}
