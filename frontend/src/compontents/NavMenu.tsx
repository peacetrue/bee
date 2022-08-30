// 导航菜单组件
import React, {MouseEventHandler, useEffect, useState} from "react";
import {Box, BoxProps, Link as MuiLink, Stack} from "@mui/material";
import {Link} from "react-router-dom";
import LaunchRoundedIcon from '@mui/icons-material/LaunchRounded';

/** 导航菜单项属性 */
export interface NavMenuItemProps {
  text: string;
  href: string;
  external: boolean;
  selected?: boolean;
  onClick?: MouseEventHandler
}

/** 导航菜单项 */
export function NavMenuItem(props: NavMenuItemProps) {
  if (props.external) {
    return (
      <MuiLink color="inherit" target={"_blank"} href={props.href} underline={"hover"}>
        {props.text}<LaunchRoundedIcon fontSize={"inherit"}/>
      </MuiLink>
    );
  }
  return (
    <MuiLink color="inherit" component={Link} to={props.href}
             underline={props.selected ? "always" : "hover"} onClick={props.onClick}>
      {props.text}
    </MuiLink>
  );
}

interface NavMenuProps extends BoxProps {
  items: NavMenuItemProps[]
}

/** 导航菜单 */
export default function NavMenu(props: NavMenuProps) {
  let [selected, setSelectedIndex] = useState(-1);
  useEffect(() => {
    document.addEventListener("clearSelected", () => setSelectedIndex(-1));
  }, []);
  return (
    <Stack ml={4} flexGrow={1} flexDirection={"row"} justifyContent={"flex-start"}>
      {props.items.map((item, index) =>
          <NavMenuItem {...item} selected={index === selected} onClick={event => setSelectedIndex(index)}/>
      )}
    </Stack>
  );
}

export const pages: NavMenuItemProps[] = [
  {text: "功能", href: "/function", external: false},
  {text: "监控", href: "/monitoring", external: false},
  {text: "文档", href: "https://peacetrue.github.io/bee", external: true},
];
