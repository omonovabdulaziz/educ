from io import BytesIO

import pandas as pd
from fastapi import FastAPI, File, UploadFile
from fastapi.responses import JSONResponse

app = FastAPI()


def handle_valid(value):
    return None if pd.isna(value) or pd.isnull(value) or value == "" else value


def is_entry_valid(entry):
    return entry["question"] is not None and any(entry["answers"].values())


@app.post("/readWord")
async def read_word_endpoint(file: UploadFile = File(...)):
    try:
        content = await file.read()
        df = pd.read_excel(BytesIO(content))

        print("Columns:", df.columns)

        questions_data = []
        for index, row in df.iterrows():
            question_data = {
                "question": handle_valid(row["QUESTION"]),
                "answers": {
                    "answer1": handle_valid(row["ANSWER1"]),
                    "answer2": handle_valid(row["ANSWER2"]),
                    "answer3": handle_valid(row["ANSWER3"]),
                    "answer4": handle_valid(row["ANSWER(TRUE)"]),
                }
            }
            if is_entry_valid(question_data):
                questions_data.append(question_data)

        return JSONResponse(content=questions_data)
    except Exception as e:
        return JSONResponse(content={"error": str(e)}, status_code=500)
