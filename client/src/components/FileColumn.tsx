import FileDto from "../store/dto/File.dto";
import FileRow from "./FileRow";

interface FileColumnProps {
  files?: FileDto[];
}

export default function FileColumn({ files }: FileColumnProps) {
  return (
    <div className="w-1/4 divide-y-2 divide-slate-500">
      <div className="flex pb-2">
        <p className="w-2/12 font-semibold text-gray-400">Id</p>
        <p className="w-6/12 font-semibold text-gray-400">File name</p>
      </div>
      <div className="flex flex-col divide-y-2 divide-slate-500">
        {files && files.length > 0
          ? files.map((file) => <FileRow key={file.fileId} file={file} />)
          : null}
      </div>
    </div>
  );
}
