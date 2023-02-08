import React from 'react';
import TreeView from '@mui/lab/TreeView';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import {Box, Paper, Stack} from "@mui/material";
import {Outlet} from "react-router-dom";
import {TreeNode, TreeNodeRender} from "../../compontents/TreeNode";
import {useTranslation} from "react-i18next";

const treeNodes: TreeNode[] = [
  {
    ns: 'conversion',
    name: 'title',
    href: '/function/conversion',
  },
];

export default function Main() {
  let {t} = useTranslation();
  let translatedTreeNodes = treeNodes.map(value => ({...value, name: t(value.name, {ns: value.ns})}))
  return (
    <Stack flexDirection={"row"}>
      <Paper sx={{
        position: "sticky",
        top: theme => theme.spacing(8), // ToolBar 的 6 + padding 的 2
        width: theme => theme.spacing(24),
        height: theme => `calc(100vh - ${theme.spacing(8)})`, // ToolBar 的 6 + padding 的 2 * 2(上下) + footer 的 8
        overflow: "auto",
      }}>
        <TreeView
          aria-label="功能菜单"
          defaultCollapseIcon={<ExpandMoreIcon/>}
          defaultExpandIcon={<ChevronRightIcon/>}
          sx={{mt: 1}}
        >
          {TreeNodeRender(translatedTreeNodes)}
        </TreeView>
      </Paper>
      <Box sx={{flexGrow: 1, ml: 2}}>
        <Outlet/> {/* 路由出口 */}
      </Box>
    </Stack>
  );
}
