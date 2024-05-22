import React, { useState } from "react";
import FileDto from "../store/dto/File.dto";
import DeleteIcon from "./icons/DeleteIcon";
import DownloadIcon from "./icons/DownloadIcon";
import EditIcon from "./icons/EditIcon";
import { useFileStore } from "../store/file-store";

interface FileRowProps {
  file: FileDto;
}

export default function FileRow({ file }: FileRowProps) {
  const { fileId } = file;
  const [isEditing, setIsEditing] = useState(false);
  const [fileName, setFileName] = useState(file.fileName);

  const { deleteFile, renameFile, downloadFile } = useFileStore();
  

  const handleFileNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setFileName(event.target.value);
  };

  const handleKeyDown = (event: React.KeyboardEvent) => {
    if (event.key === "Enter") {
      setIsEditing(false);
      renameFile(fileId, fileName);
    }
  };

  return (
    <div className="flex py-2">
      <p className="w-2/12">{fileId}</p>
      {isEditing ? (
        <input
          type="text"
          value={fileName}
          onChange={handleFileNameChange}
          onKeyDown={handleKeyDown}
          className="w-3/5"
        />
      ) : (
        <p className="w-3/5">{fileName}</p>
      )}
      <div className="flex space-x-2">
        <DownloadIcon onClick={() => downloadFile(fileId)} /> 
        <EditIcon onClick={() => setIsEditing(true)} />
        <DeleteIcon onClick={() => deleteFile(fileId)} />
      </div>
    </div>
  );
}
