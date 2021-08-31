import React, { useContext, useEffect, useState } from 'react'
import ReactMarkdown from 'react-markdown'
import { useParams, Link } from 'react-router-dom'
import { Button, Divider, Spin } from 'antd'
import { ArrowLeftOutlined, DownloadOutlined } from '@ant-design/icons'
import { DOCUMENTS_PAGE } from '../../Routes/routes.paths'
import { documentHeaderStyle, documentTextStyle } from './Document.styles'
import { dividerStyle } from '../../components/DocumentsList/DocumentsList.style'
import { useHttp } from '../../hooks/http.hook'
import { AuthContext } from '../../contexts/AuthContext'

const Document = () => {
  const { loading, request } = useHttp()
  const [document, setDocument] = useState({})
  const auth = useContext(AuthContext)
  const params = useParams()

  useEffect(() => {
    const getDocumentById = async () => {
      const data = await request(`/ml/document/${params.id}`, 'GET', null, {
        Authorization: `Bearer ${auth.accessToken}`
      })
      setDocument(data)
    }

    getDocumentById()
  }, [params.id, request, auth.accessToken])

  return (
    <>
      <div css={documentHeaderStyle}>
        <Link to={DOCUMENTS_PAGE}>
          <Button icon={<ArrowLeftOutlined />}>Назад</Button>
        </Link>
        <div>{document.title}</div>
        <Button icon={<DownloadOutlined />}>Скачать документ</Button>
      </div>
      <Divider css={dividerStyle} />
      <div>
        {loading ? (
          <Spin />
        ) : (
          <div css={documentTextStyle}>
            <ReactMarkdown>{document.text}</ReactMarkdown>
          </div>
        )}
      </div>
    </>
  )
}

export default Document
