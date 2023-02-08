import {Link as MuiLink} from "@mui/material";
import {Link} from "react-router-dom";
import TreeItem from "@mui/lab/TreeItem";
import React from "react";

export interface TreeNode {
  id?: string;
  ns: string;
  name: string;
  href?: string;
  children?: readonly TreeNode[];
}

export function TreeNodeRender(nodes: TreeNode | TreeNode[]): React.ReactNode {
  if (nodes instanceof Array) {
    return <>{nodes.map(node => TreeNodeRender(node))}</>;
  }

  const id = nodes.id || nodes.href || '';
  const label = nodes.href
    ? (<MuiLink component={Link} to={nodes.href} underline={"hover"}>{nodes.name}</MuiLink>)
    : nodes.name;
  return (
    <TreeItem key={id} nodeId={id} label={label}>
      {Array.isArray(nodes.children)
        ? nodes.children.map((node) => TreeNodeRender(node))
        : null}
    </TreeItem>
  )
}

export default TreeNode;
