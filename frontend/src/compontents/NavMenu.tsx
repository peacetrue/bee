// 导航菜单组件
import React, {MouseEventHandler, useEffect, useState} from "react";
import {Grid, Link as MuiLink} from "@mui/material";
import {Link} from "react-router-dom";

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
  let underline: 'none' | 'hover' | 'always' = props.selected ? "always" : "hover";
  return props.external
    ? (<MuiLink color="inherit" target={"_blank"} href={props.href} underline={underline}
                onClick={props.onClick}>{props.text}</MuiLink>)
    : (<MuiLink color="inherit" component={Link} to={props.href} underline={underline}
                onClick={props.onClick}>{props.text}</MuiLink>);
}

/** 导航菜单 */
export default function NavMenu(props: { items: NavMenuItemProps[] }) {
  let [selected, setSelectedIndex] = useState(-1);
  useEffect(() => {
    document.addEventListener("clearSelected", () => setSelectedIndex(-1));
  }, []);
  return <Grid container ml={4} spacing={4}>
    {props.items.map((item, index) =>
      (<Grid item>
        <NavMenuItem {...item} selected={index === selected} onClick={event => setSelectedIndex(index)}/>
      </Grid>)
    )}
  </Grid>;
}
