import React, { useState } from "react";
import FileDto from "../store/dto/File.dto";
import DeleteIcon from "./icons/DeleteIcon";
import DownloadIcon from "./icons/DownloadIcon";
import EditIcon from "./icons/EditIcon";

interface FileRowProps {
  file: FileDto;
}

export default function FileRow({ file }: FileRowProps) {
  const { fileId, fileUrl } = file;
  const [isEditing, setIsEditing] = useState(false);
  const [fileName, setFileName] = useState(file.fileName);

  const downloadFile = async () => {
    try {
      const response = await fetch(fileUrl);
      if (!response.ok) throw new Error('Network response was not ok.');
      const blob = await response.blob();
      const url = window.URL.createObjectURL(blob);
      const a = document.createElement('a');
      a.href = url;
      a.download = fileName || 'download';
      document.body.appendChild(a);
      a.click();
      window.URL.revokeObjectURL(url);
      document.body.removeChild(a);
    } catch (error) {
      console.error('There was an error!', error);
    }
  };
  

  const handleFileNameChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setFileName(event.target.value);
  };

  const handleKeyDown = (event: React.KeyboardEvent) => {
    if (event.key === "Enter") {
      setIsEditing(false);
      // TODO: Add saving the new name to the server
    }
  };

  const removeFile = () => {};

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
        <a>
        <DownloadIcon onClick={downloadFile} />
        </a>
        
        <EditIcon onClick={() => setIsEditing(true)} />
        <DeleteIcon onClick={removeFile} />
      </div>
    </div>
  );
}
