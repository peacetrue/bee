import {
  Box,
  Button,
  FormControl,
  FormHelperText,
  InputLabel,
  MenuItem,
  Select,
  Stack,
  TextField,
  Typography
} from "@mui/material";
import React, {useEffect} from "react";
import Axios from "../../infrastructure/Axios";
import {Controller, FieldValues, useForm} from "react-hook-form";
import {UseFormReturn} from "react-hook-form/dist/types";
import {yupResolver} from '@hookform/resolvers/yup';
import * as yup from "yup";
import {useTranslation} from "react-i18next";

interface DataPanelProps {
  label: string;
  name: string;
  form: UseFormReturn;
  formats: string[];
}

function DataPanel(props: DataPanelProps) {
  let {t} = useTranslation("conversion");

  const {label, name, form: {control}, formats} = props;
  return (
    <Stack spacing={2}>
      <Box>
        <Typography variant={"subtitle1"}>{label}</Typography>
      </Box>
      <Box>
        <Controller
          name={`${name}.format`} control={control}
          render={({field, fieldState, formState}) =>
            <FormControl fullWidth error={!!fieldState.error}>
              <InputLabel>{t("format")}</InputLabel>
              <Select {...field} label={t("format")}>
                <MenuItem value=""><em>{t("unselected", {ns: "bee"})}</em></MenuItem>
                {formats.map(item => <MenuItem key={item} value={item}>{item}</MenuItem>)}
              </Select>
              <FormHelperText>{fieldState.error?.message}</FormHelperText>
            </FormControl>
          }/>
      </Box>
      <Box>
        <Controller
          name={`${name}.content`} control={control}
          render={({field, fieldState, formState}) =>
            <TextField label={t("content")} multiline minRows={20} fullWidth
                       {...field} error={!!fieldState.error} helperText={fieldState.error?.message}
            />
          }
        />
      </Box>
    </Stack>
  );
}

const user_json = `{
  "name" : "张三",
  "age" : 18,
  "roles" : [ {
    "name" : "admin",
    "remark" : "管理员"
  }, {
    "name" : "employee",
    "remark" : "员工"
  } ],
  "tags" : [ "goodness", "tall" ]
}`;

export default function Conversion() {
  let {t} = useTranslation("conversion");
  const [formats, setFormats] = React.useState<string[]>([]);
  useEffect(() => {
    Axios.get("/conversion/formats").then(response => setFormats(response.data));
  }, []);

  const schema = yup.object({
    "source": yup.object({
      "format": yup.string().required(),
      "content": yup.string().required().max(1024 * 512),
    }),
    "target": yup.object({
      "format": yup.string().required(),
    })
  });

  let defaultValues: FieldValues = {source: {format: "", content: ""}, target: {format: "", content: ""}};
  if (process.env.NODE_ENV === "development") {
    defaultValues.source.format = "json";
    defaultValues.source.content = user_json;
    defaultValues.target.format = "yaml";
  }
  const form = useForm({
    resolver: yupResolver(schema),
    defaultValues: defaultValues
  });
  return (
    <Stack spacing={1}>
      <Box><Typography variant={"h6"}>{t("description")}</Typography></Box>
      <Box component={"form"} onSubmit={form.handleSubmit((data) => {
        //target 的内容不需要传回后台
        data = {source: data.source, target: {format: data.target.format, content: ""}};
        Axios.post("/conversion", data)
          .then(response => form.setValue("target.content", response.data));
      })}>
        <Stack sx={{flexDirection: "row", alignItems: "center"}}>
          <Box sx={{flexGrow: 1}}>
            <DataPanel label={t("source")} name={"source"} form={form} formats={formats}/>
          </Box>
          <Box sx={{m: 1}}>
            <Button type="submit" size="medium" variant={"outlined"}> &gt; </Button>
          </Box>
          <Box sx={{flexGrow: 1}}>
            <DataPanel label={t("target")} name={"target"} form={form} formats={formats}/>
          </Box>
        </Stack>
      </Box>
    </Stack>
  );
}
