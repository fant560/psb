import { Badge, Card, List } from "antd"
import { useHistory } from "react-router"
const DocumentGrid = ({ documents, isLoading }) => {
    const history = useHistory()
    return (<List
        grid={{ gutter: 16, column: 4 }}
        dataSource={documents}
        loading={isLoading}
        renderItem={(item, index) => (
            <>
                <List.Item
                    onClick={() => history.push(`/documents/${item.id}`)}
                >
                    <Badge.Ribbon text={item.isSuccess ? "Документ валиден" : "Докумен не валиден"} color={item.isSuccess ? "green" : "red"}>
                        <Card title={item.title} description={item.description} loading={isLoading}>
                        </Card>
                    </Badge.Ribbon>
                </List.Item>
            </>
        )}
    />
    )
}
export default DocumentGrid