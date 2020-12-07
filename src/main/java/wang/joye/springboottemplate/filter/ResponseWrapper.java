package wang.joye.springboottemplate.filter;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @author 汪继友
 * @since 2020/8/21
 */
public class ResponseWrapper extends HttpServletResponseWrapper {
    private final ByteArrayOutputStream buffer;
    private final ServletOutputStream out;

    public ResponseWrapper(HttpServletResponse httpServletResponse) throws IOException {
        super(httpServletResponse);
        // 构建空buffer，用于存储实际数据
        buffer = new ByteArrayOutputStream();
        // 使用包装输出流，关联到buffer上，同时输出到response的outputStream中
        out = new WrapperOutputStream(buffer, httpServletResponse.getOutputStream());
    }

    /**
     * 返回自定义的ServletOutPutStream
     */
    @Override
    public ServletOutputStream getOutputStream() {
        return out;
    }

    public String getResponseData() throws IOException {
        flushBuffer();
        return buffer.toString();
    }

    /**
     * 自定义输出流，输出到buffer，并且输出到ServletOutputStream上
     */
    static class WrapperOutputStream extends ServletOutputStream {
        private final ServletOutputStream responseOutputStream;
        private final ByteArrayOutputStream bos;

        public WrapperOutputStream(ByteArrayOutputStream bos, ServletOutputStream responseOutputStream) {
            this.bos = bos;
            this.responseOutputStream = responseOutputStream;
        }

        @Override
        public void write(int b) throws IOException {
            bos.write(b);
            responseOutputStream.write(b);
        }

        @Override
        public boolean isReady() {
            return false;
        }

        @Override
        public void setWriteListener(WriteListener listener) {

        }

        @Override
        public void flush() throws IOException {
            bos.flush();
            responseOutputStream.flush();
        }

        @Override
        public void close() throws IOException {
            bos.close();
            responseOutputStream.close();
        }
    }
}

