import React from 'react';
import TreeView from '@mui/lab/TreeView';
import ExpandMoreIcon from '@mui/icons-material/ExpandMore';
import ChevronRightIcon from '@mui/icons-material/ChevronRight';
import {Box, Stack} from "@mui/material";
import {Outlet} from "react-router-dom";
import {TreeNodeRender, TreeNode} from "../../compontents/TreeNode";

const treeNodes: TreeNode[] = [
  {
    name: '转换器',
    href: '/function/converter',
  },
  {
    id: 'codec',
    name: '编解码',
    children: [
      {
        name: '字符串',
        href: '/function/codec',
      },
    ],
  }
];

export default function Main() {
  return (
    <Stack flexDirection={"row"}>
      <TreeView
        aria-label="功能菜单"
        defaultCollapseIcon={<ExpandMoreIcon/>}
        defaultExpandIcon={<ChevronRightIcon/>}
        sx={{
          position: "sticky",
          top: theme => theme.spacing(8), // ToolBar 的 6 + padding 的 2
          width: theme => theme.spacing(24),
          height: theme => `calc(100vh - ${theme.spacing(18)})`, // ToolBar 的 6 + padding 的 2 * 2(上下) + footer 的 8
          overflow: "auto",
        }}
      >
        {TreeNodeRender(treeNodes)}
      </TreeView>
      <Box sx={{flexGrow: 1, ml: 1}}>
        {/* 路由出口 */}
        <Outlet/>
      </Box>
    </Stack>
  );
}
